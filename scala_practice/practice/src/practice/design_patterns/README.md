
# Gang of Four Patterns
* **Creational** - hides details about concrete classes, object creation
  * **Abstract factory** - groups factories with common theme
  * **Factory method** - creates object w/o specifying concrete class
  * **Lazy init** - delays creating object or evaluating a value until first time it's used
  * **Singleton** - one instance of an object
  * **Object pool** - pool of pre-instantiated objects
  * **Builder** - creates objects using different combinations of params
  * **Prototype** - clones an existing object that is expensive to create
* **Structural** - allows flexible connecting of components to form large systems
  * **Adapter** - wraps a class interface inside another interface, typically used to integrate with libraries you can't modify, implemented with Scala implicit classes
  * **Decorator** - wraps a class object with another class to extend functionality, implemented with Scala stackable traits
  * **Bridge** - de-couples an interface from concrete classes, used if need to evolve each independently, different from adapter in that you can change the concrete classes, implemented with Scala self-types
  * **Composite** - allows you to interact with an object or group of objects using the same interface (e.g., tree nodes)
  * **Facade** - hides multiples components behind a simpler interface
  * **Flyweight** - provides a shared object to reduce memory usage
  * **Proxy** - wraps objects (like shared objects) inside an interface
* **Behavioral** - patterns that describe a communication process or flow between objects
  * **Value object** - immutable objects whose equality is based on fields being equal, e.g., DTOs, dates, money amounts, etc.
  * **Null object** - represents absence of value, implemented with Scala optional values
  * **Strategy** - select algorithm at runtime, implemented in Scala by passing function param to method
  * **Command** - object that stores info about an action to be triggered later, implementing using Scala by-name params
  * **Chain of Responsibility** - chain of receiver objects that each process (or pass) the request to the next receiver, implemented using Scala stackable traits
  * **Interpreter** - interpret domain-specific language
  * **Iterator** - used to sequentially traverse a container
  * **Mediator** - encapsulates communication between different classes in application
  * **Memento** - roll back an object to its previous state
  * **Observer** - pub/sub pattern, related to reactive programming enabled by Akka
  * **State** - encapsulate different behavior for each state, avoid need for large 'if' statements
  * **Template method** - general algorithm where some steps are passe to subclasses
  * **Visitor** - defines new operation on elements of an object w/o changing the object's class
  

# Scala Patterns
* Functional
  * **Monoids** - convert pairwise operations to work with sequences, associativity allows for parallelization
  * **Monads** - structures representing computations as sequences of steps
  * **Functors**
* Scala-specific
  * **Lens** - patterns to achieve mutability
  * **Cake** - implements DI
  * **Pimp my library** - write extensions to libraries we cannot modify
  * **Stackable traits** - implement decorator pattern and compose functions
  * **Type class design pattern** - defines behavior that must be supported by all members of a specific type class
  * **Lazy Evaluation**
  * **Partial Functions**
  * **Implicit Injection** - can be used to implement DI
  * **Duck Typing** - requires classes to have some specific methods but not implement interface, like Python duck typing
  * **Memoization** - optimize by remembering function results, based on the inputs
