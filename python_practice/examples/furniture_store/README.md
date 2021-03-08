
# Furniture Store Example
* Example from the Architecture Patterns with Python book
* Aims to build a flexible application architecture that supports:
  * Domain Modeling
  * Event-driven requirements
* Uses these techniques:
  * DDD
  * TDD

# Setup
```
python -m venv .venv
```

# Ch 1 - Domain Modeling
* Goal: Build a new allocation system for an online furniture store that calculates stock based on shipments, instead of what's only in the warehouse
* Domain language
  * Come up with rules for domain model
  * Rules:
    * allocate order lines (customer order) to a batch. batch has a SKU and quantity
    * prefer allocating to batches with earliest ETA
 