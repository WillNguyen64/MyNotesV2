
# --- References ---

* System Design Interview: An Insider's Guide

# --- General Scaling Techniques ---

* Keep web tier stateless
* Build redundancy at every tier
* Cache data as much as possible
* Support multiple DCs
* Host static assets in CDN
* Scale data tier by sharding
* Split tiers into individual services
* Monitor system and use automation tools


# --- Back of Envelope Estimations ---

* Use this to estimate system capacity or performance requirements
* Based on thought experiments and common performance/latency numbers to get good feel of which design will meet requirement

## Power of two

Data volume units are expressed
* 2^10 bytes = 1KB  (power = 10)
* 2^20 bytes = 1MB  (power = 20)
* 2^30 bytes = 1GB  (...)
* 2^40 bytes = 1TB
* 2^50 bytes = 1PB


## Latency numbers every programmer should know

* L1 cache reference: 0.5 ns
* Branch mispredict: 5 ns
* L2 cache reference: 7 ns
* Mutex lock/unlock: 100 ns
* Main memory ref: 100 ns
* Compress 1KB bytes with Zippy = 10K ns = 10 microsec
* Send 2K bytes over 1 Gbps network = 20K ns = 20 microsec
* Read 1 MB sequentially from memory: 250K ns = 250 microsec
* Round trip with the same datacenter: 500K ns = 500 microsec
* Disk seek: 10M ns = 10 ms
* Read 1 MB sequentially from the network: 10M ns = 10 ms
* Read 1 MB sequentially from disk: 30M ns = 30 ms
* Send package CA (California) -> Netherlands -> CA: 150M ns = 150 ms


## Availability numbers

* 99% = 14.4 mins down / day = 3.65 days down / day
* 99.9% = 1.44 mins = 8.77 hours
* 99.99% = 8.64 sec = 52.60 mins
* 99.999% = 864 msec = 5.26 mins
* 99.9999% = 86.40 msec = 31.56 sec


## Example: Estimate Twitter QPS and storage requirements

Assumptions:
* 300 M monthly active users
* 50% of users use Twitter daily
* Users post 2 tweets per day on average
* 10% of tweets contain media
* Data is stored for 5 years

Estimations:
* QPS: 
  * Daily Active users (DAU): 300M * 50% = 150M
  * Tweets QPS = 150M * 2 tweets / 24 hr / 3600 sec =~ 3500
  * Peek QPS = 2 * QPS =~ 7000
* Media storage estimate:
  * Avg tweet size
    * tweet_id: 64 bytes
    * text: 140 bytes
    * media: 1MB
  * Media storage: 150M * 2 * 10% * 1 MB= 30 TB per day
  * 5-yr media storage: 30 TB * 365 * 5 = ~55 PB

## Tips for doing back-of-the-envelope estimates

* Round/approximate calculations
* Write down assumptions
* Label units, e.g., 5KB or 5MB?
* Commonly asked estimations: QPS, peak QPS, storage, cache, number of servers, etc


# --- System Design Interviews ---

* What is it:
  * Given open-end problem with no perfect answer
  * Simulates real-life problem solving where 2 co-workers collaborate on an ambiguous problem and come up with solution
  * Final design is less important compared to work put in the design process

* Helps demonstrate:
    * Design skills, defend your design choices, respond to feedback in constructive manner
    * Collaborative skills, working under pressure, resolve ambiguity constructively
    * Any red flags like over-engineering, narrow mindedness, stubbornness, etc.

## Four step process:

1. Understand the problem and establish design scope (3 - 10 mins)
* Don't jump right into a solution; think deeply and clarify requirements and assumptions
* Interviewer either answers question directly or asks you to make your assumptions
  * Write down assumptions!!
* Questions to ask:
  * What features to build?
  * How many users does product have?
  * How fast does company want to scale up?  Scales in 3, 6, 12 months?
  * Company's technology stack? Existing services you might leverage to simplify the design?
* Example: Design a news feed system:
  * Q: Mobile or web app? Or both?  Both
  * Q: Most important features for product? Make post, see friends' news feed
  * Q: News feed sorted in reverse chronological order or particular order (e.g., assigned higher weight to posts by close friends)?  The former
  * Q: How many friends can a user have?  5K
  * Q: What is the traffic volume?  10M DAUs
  * Q: Can feed contain images, videos, or just text?  Image/video

2. Propose a high-level design and get buy-in  (10-15 mins)
* Create initial blueprint, ask for feedback (collaborate)
* Draw box diagrams on paper with clients (mobile/web), APIs, web servers, data stores, cache, CDN, msg queues, etc.
* Do back-of-the-envelope calcs to check if blueprint fits scale constraints. Check with interviewer if this is needed
* Run thru a few use cases. 
* Example: Design a news feed system
  * High-level design for 2 flows:
    * Feed publishing
      * When user publishes a post, store this data into cache/DB, post is populated into friend's news feed
      * Diagram: User (web/mobile) -> LB (REST API call) -> Web -> {Post Service, Fanout Service, Notification Service}
    * News feed building
      * Build news feed by aggregating friends' posts in reverse chronological order
      * Diagram: User -> LB (REST API call) -> Web -> {News Feed Service}

3. Design deep dive (10 - 25 mins)
* At this point, you should have agreed upon goals/scope, high-level design, gotten feedback, initial ideas about areas to
focus on based on feedback
* Identify/prioritize mot critical components in architecture
  * For senior candidates, dig into system performance, bottlenecks, resource estimates
  * Dig into details of some system components
    * e.g., For URL shortner: dive into hash function, For chat system, dive into supporting online/offline status
* DO NOT GO INTO UNNECESSARY DETAILS
* Example:
  * Deep-dive into Feed Publishing
    * Web service: use Auth, rate limiting
    * Post Service: write to cache, then DB
    * Fanout service: 
      * 1. Get friend ids (from Graph DB)
      * 2. Get friend data (from cache, or from DB on cache miss)
      * 3. Write request to MQ to update news feed cache using multiple Fanout Workers
  * Deep-dive into News feed retrieval
    * 1. Get news feed from my own feed
    * 2. Get my friends news feeds

4. Wrap-up (3-5 mins)
* Few follow-up questions or discussion about additional points:
  * Identify system bottlenecks, potential improvements
  * Recap design (esp if you suggested a few solutions)
  * Interesting error cases (server failure, network loss, etc)
  * Operational issues (monitoring, error logs, etc.), system rollout
  * Handle next level of scale? If you system handles 1M users now, how to handle 10M?
  * Propose other refinements


# --- Web Layer ---

## Stateful Arch
* Requests must be sent to a specific server which remembers client data (state) across requests (e.g., user session / profile data)
* Implemented using sticky sessions in LBs
* Con: Adding/removing servers hard, Hard to deal with server failures

## Stateless Arch
* Requests can be sent to any web server, which reads data from shared DB
* Pro: Easy to scale out the web tier as traffic goes up


# --- Databases ---

## Sharding
* Sharding key (i.e., partition key) is important as it determines how data is distributed
* Challenges:
  * Resharding data
    * Due to uneven data distribution causing shard exhaustion
    * To solve, need to update sharing function and re-shuffle data
      * Consistent hashing commonly solves this problem
  * Celebrity / hotspot key problem
    * Shard containing heavily read data will be overwhelmed
    * To solve: allocate shard for each celebrity
  * Join and de-normalization
    * Data across shards cannot be easily joined
    * To workaround: de-normalize the DB so queries can be performed in a single table


# --- Message Queue ---

* Used for: de-coupling services in distributed system
* Scale out workers when queue becomes large, scale in workers when queue has less msgs


# --- Caching ---

## Benefits
* Reduce response time
* Decrease load on DB
* Reduce costs, e.g., DynamoDB charges per request, so less requests == lower costs

## Caching considerations
* Typically use caching when data is read more than it is written
* Choose caching strategy based on data access pattern
* Expiration policy
* Consistency
* Mitigating failures (e.g., SPOF)
* Eviction policy
  * When cache is full, decide how to evict existing data to make room for new data
  * Policies: LRU (most popular), LFR, FIFO

## Caching strategies
* Cache-Aside
  * App talks to both cache and DB. On cache miss, app populates missing data from DB
  * Used for: read-heavy workloads
  * Pro: data model can be diff in cache and DB
  * Con: cache may be inconsistent with DB, use TTL to invalidate/refresh cache
* Read-Through Cache
  * App reads from cache. On cache misses, cache populates missing data from DB
  * Used for: read-heavy workloads
  * Pro: app is de-coupled from DB
  * Con: data is populated on first read, can take long time; avoid by warming cache
  * Con: data model must be same in cache and DB
  * Con: cache may be inconsistent with DB; app can write to DB w/o updating cache (i.e, Write-Around)
* Write-Through Cache
  * Data is written to cache, then to DB; e.g., DynamoDB Accelerator (DAX)
  * Used for: ...
  * Pro: Combine w/ Read-Through Cache to get consistency guarantee
* Write-Around
  * Write data directly to DB, only data that is read is saved to cache
  * Used for: cases where data written once, read less frequently or never (e.g., real-time logs, chatroom msgs)
  * Pro: Combine w/ Read-Through for performance, can also combine with Cache-Aside
* Write-Back / Write-Behind
  * Write data to cache, ack immediately, write back data to DB some delay
  * Used for: write-heavy workload
    * e.g., use Redis for both cache-aside and write-back to absorb spikes during peak load (but can lose data if cache fails)
    * e.g., Most SQL DB engines (e.g., InnoDB) use write-back by default. Queries first written to memory, then flushed later to disk.
  * Pro: Combine w/ Read-Through to make most recently updated and accessed data is always avail in cache
* Refs:
  * https://codeahoy.com/2017/08/11/caching-strategies-and-how-to-choose-the-right-one/
  

# -- Logging and Monitoring --

* Use tools to aggregate server logs to a central server for searching/viewing
* Collect system/health and business metrics
  * Host level: CPU, mem, disk I/O, etc.
  * Aggregate: performance of entire DB tier, cache tier, etc.
  * Key business metrics: daily active users, retention, revenue, etc.

# -- Data Centers --

* Use multiple DCs to improve availability and better UX across regions
* Users are geo-routed to nearest data center (via geoDNS)
* If one DC goes down, direct all traffic to the healthy DC

## Challenges

* Traffic redirection (e.g., GeoDNS)
* Data sync
  * Users in diff regions could use diff local DB / caches
  * If failure happens, user might be routed to DB where DB is unavail
  * Strategy: replicate data across DCs
* Test/Deployment
  * Keep services consistent through all DCs
