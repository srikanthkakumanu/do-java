<div text-align="justify">

# **Records in Java**

## Tuple

_Data aggregation_ is the process of gathering data from multiple sources and assembling it in a format. 
The most well known **data aggregation type is tuple**. 

**Tuple** — Mathematically speaking, a **tuple is a finite-ordered sequence of elements**. 
In programming, **a tuple is a data structure aggregating multiple values or objects**.<br> 

There are two kinds of tuples: **Structural tuples and Nominal tuples.**<br>

**Structural tuples** — rely on the order of the contained elements and are therefore accessible only via their indices.
(Python supports it)<br>
**Nominal tuples** — don't use an index to access their data but use component names.<br>
**Dynamic tuples** — **This feature is missing in Java**, Other programming languages usually use those as dynamic data aggregators without requiring an explicitly defined type. 
Java Records are simple data aggregators and can be **considered nominal tuples**.
But we can use Java records as localized on-the-fly data aggregators (Local Records are records that are defined within a method). Contextually localized Records simplify and formalize data processing and bundle functionality.

## Records

**Record is a plain data aggregator type, and the purpose is to carry just the data and no boilerplate code is required.**
They are **immutable data classes** that require only the type and field names and no setXXX() are generated. 
However, if a record holds a reference to some object, you can make a change to that object, but you cannot change to what object the
reference in the record refers. Thus, in Java terms, **records are said to be shallowly immutable**.

In short, **A record is a nominal tuple**. 
Like nominal tuples, Records aggregate an ordered sequence of values and provide access via names instead of indices.

Once the Record is declared — compiler generates:
- `equals()`, `toString()`, `hashCode()` methods.
- private and final fields.
- public canonical constructor (with fields defined at record declaration level).

### Records—Canonical, compact and custom constructors

**Canonical Constructor** —
A constructor identical to the Record's component's definition is automatically generated/available, 
called the canonical constructor.<br>

**Compact Canonical Constructor** — A compact form of canonical constructor declaration.
- The constructor omits all the arguments, including the parentheses.
- Field assignments are not allowed in the compact canonical constructor, but you can customize or normalize data before it’s assigned.
- The components will be assigned to their respective fields automatically, no additional code required.
**Note: We can define either a standard canonical or compact canonical constructor but not both in the same record.**<br>

**Custom Constructors** — We can define several custom constructors by using standard form or in custom form but must start with an explicit invocation of the canonical constructor as its first statement.


1. By default, All record declarations implicitly inherit from `java.lang.Record`. So, **it does not support multiple inheritance**.
2. By default, all records create a canonical constructor with the arguments that are defined at declaration level. But we can override that canonical constructor.
3. We can **override the default canonical constructor with Compact constructor as well**. This is special to records only and useful to override constructors in more compact way.
4. We can override the default canonical constructor either by using a _standard canonical_ form or _compact canonical_ form but cannot define both in the same record.
5. We can define custom constructors, i.e., **multiple constructors with different combinations of arguments**.
6. Records **can implement interfaces**.
7. By default, **All records are _final_ hence it cannot be extended further**.
8. Once declared, **all the record fields are immutable hence cannot be changed**. Hence, **they are thread-safe and thereby, no synchronization is required**.
9. It allows **only static constants to be declared**. no instance level fields (non-static) are allowed. Any fields are needed, they should be declared as arguments at record declaration level.
10. A record also supports generics (it can be generic) i.e. it supports generic just like any other types. However, **A constructor cannot be generic, and it cannot include a _throws_ clause either at constructor or getter method level**.
11. We can create a record in step-by-step fashion by using a _Builder design pattern_.
12. Records are the best place to put validations and scrubbing logic. Throwing Exceptions is one way to go. Another option is to scrub the data and adjust component values with sensible alternatives to form a valid Record.
13. Records **can be Serialized** by using `java.io.Serializable`.
14. **RecordBuilder annotation** generates a flexible builder for any Record, and all you have to do is add a single annotation. Note: this is not part core JDK and Refer to https://github.com/randgalt/record-builder
15. **Dynamic tuples (Local Records)** — **This feature is missing in Java**. Other programming languages usually use those as dynamic data aggregators without requiring an explicitly defined type. **Java Records are simple data aggregators and can be considered nominal tuples. But we can use Java records as localized on-the-fly data aggregators (Local Records are records that are defined within a method)**. Contextually localized Records simplify and formalize data processing and bundle functionality. 


RecordBuilder Example: -

```text
@RecordBuilder
public record Point(int x, int y) {
  // NO BODY
}

// GENERAL BUILDER
var original = PointBuilder.builder()
                           .x(5)
                           .y(23)
                           .build();
// => Point[x=5, y=23]

// COPY BUILDER
var modified = PointBuilder.builder(original)
                           .x(12)
                           .build();
// => Point[x=12, y=23]
```
</div>