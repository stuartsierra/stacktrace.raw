# com.stuartsierra/stacktrace.raw

Undo Clojure stacktrace pretty-printers.



## Releases and Dependency Information

No published releases yet.
Run `lein install` in this directory and use the -SNAPSHOT version.

[Leiningen] dependency information:

    [com.stuartsierra/stacktrace.raw "0.1.0-SNAPSHOT"]

[Maven] dependency information:

    <dependency>
      <groupId>com.stuartsierra</groupId>
      <artifactId>stacktrace.raw</artifactId>
      <version>0.1.0-SNAPSHOT</version>
    </dependency>

[Leiningen]: http://leiningen.org/
[Maven]: http://maven.apache.org/



## What This Does

stacktrace.raw checks for the presence of other Clojure libraries
which reformat stack traces for display:

- Included with Clojure
  - [clojure.main](http://clojure.github.io/clojure/clojure.main-api.html)
  - [clojure.repl](http://clojure.github.io/clojure/clojure.repl-api.html)
  - [clojure.stacktrace](http://clojure.github.io/clojure/clojure.stacktrace-api.html)
- Third-party libraries
  - [clj-stacktrace](https://github.com/mmcgrana/clj-stacktrace)
  - [io.aviso/pretty](https://github.com/AvisoNovate/pretty)
  - [clj-pretty-error](https://github.com/liquidz/clj-pretty-error)
  - [ring/ring-devel](https://github.com/ring-clojure/ring/tree/master/ring-devel)

When it finds one of those libraries, it redefines its public
functions so that they do the minimum possible: calling Java's
`printStackTrace` method.

stacktrace.raw attempts to preserve other semantics of the functions
it redefines: returning a string, printing to `*out*`, printing to
`*err*`, and so on. It omits all other features: name munging, frame
omission, cause unwrapping, columnar output, and colorizing.

In the case of ring/ring-devel, stacktrace.raw redefines the
`ring.middleware.stacktrace/wrap-stacktrace-web` middleware to catch
**all** java.lang.Throwable, not just subclasses of
java.lang.Exception. When it catches a throwable, it will return an
HTTP 500 response with the raw stacktrace as plain text.

If you find yourself working in a project which has one of these
libraries in its dependency tree, you can add stacktrace.raw to
override it.



## Usage

To use stacktrace.raw in your application, add it as a dependency and
`require` the namespace `com.stuartsierra.stacktrace.raw`.

    (require 'com.stuartsierra.stacktrace.raw)

That's it. stacktrace.raw will search for all the stacktrace
pretty-printers it knows about (see list above) and redefine their
public Vars to be functions that call `.printStackTrace`.


### Leiningen User Profile

If you are using Leiningen, you can apply stacktrace.raw globally by
loading it in your `:user` profile.

Merge the following into your `~/.lein/profiles.clj` file:

    {:user
     {:dependencies [[com.stuartsierra/stacktrace.raw "0.1.0-SNAPSHOT"]]
      :injections [(require 'com.stuartsierra.stacktrace.raw)]}}



## Why This Exists

Exceptions are tricky things. By definition, they only appear when
something has gone wrong. It's good that we have exceptions: we don't
want a program to continue running in an invalid or corrupted state.

Of course, the way most programmers experience exceptions is through
their printed form, the *stack trace*. Stack traces often evoke a
sense of dread, particularly while learning a new programming
language.

Coming to a JVM-based language from an interpreted one such as Ruby or
Python, the stack traces produced by the JVM may seem unusually dense.
Due to the breadth and complexity of the Java platform, a Java method
call often occurs dozens or even hundreds of levels "deep" in the
stack. The JVM usually optimizes these deep call stacks via inlining,
but they're still present in the stack trace.

Clojure on the JVM does not have its own stack: it uses the JVM stack.
This is an important feature; without it Clojure would be unable to
take advantage of all the excellent optimization work that has been
done on the JVM. However, from the point of view of someone learning
Clojure, the stack traces seem even more impenetrable because they are
filled with details of Clojure's internals. Clojure functions appear
in the stack trace under their compiled Java names, "munged" to fit
into Java identifiers.

With the goal of making Clojure easier to learn or more pleasant to
work with, several Clojure libraries change how stack traces are
printed. (Java stack traces are represented as objects in the JVM, so
it is possible to write code to interpret them.) These libraries
typically do one or more of the following:

1. Convert "munged" Java identifiers back to their Clojure names

2. Omit "uninteresting" stack frames related to Clojure internals

3. "Unwrap" nested exceptions to find the "root cause" exception

4. Reformat the printed representation of a stack trace, adding colors
   or lining up similar elements in columns

Many Clojure programmers, both new and experienced, have found these
libraries to be a valuable part of their development workflow.

I recognize the temptation to "fix" Java stacktraces in Clojure
libraries. I wrote one myself, [clojure.contrib.stacktrace], later
[moved] into Clojure as [clojure.stacktrace].

[clojure.contrib.stacktrace]: https://github.com/clojure/clojure-contrib/commit/6e919e57d9fe856007ce9579ff903a4257ce75f1
[moved]: https://github.com/clojure/clojure/commit/a12092c5510e8e002e7f0ef1bc53340ba2d7e473
[clojure.stacktrace]: https://github.com/clojure/clojure/blob/201a0dd9701e1a0ee3998431241388eb4a854ebf/src/clj/clojure/stacktrace.clj

However, while the Java exception interfaces are well-documented,
their guarantees are not. It is possible to get an exception object
with a stack trace that is empty, null, or contains elements that do
not match the usual format.

When pretty-printer libraries encounter exceptions they were not
designed to handle, they often break. When that happens, of course,
another exception gets thrown, **replacing** the original exception.

Time and time again I have run into mysterious NullPointerExceptions
in my applications, only to discover that some stacktrace
pretty-printer failed to format an unusual exception, thereby
**discarding** the original exception.

As a result, I am now deeply suspicious of any code which attempts to
manipulate Java stack traces. I would much rather have an ugly,
verbose, **reliable** exception reporting mechanism than something
which is easier to read but occasionally loses critical information.
(It's hard to make repeatable test cases for these errors when they
are related to JVM optimizations such as OmitStackTraceInFastThrow.)

As a consultant, I often work on Clojure code bases which are not my
own. Exception pretty-printers often appear in these projects as
development-time dependencies of other tools or libraries. I wrote
stacktrace.raw so I can avoid exception pretty-printers without
altering the dependencies of the project I am working on.



## Change Log

* Version 0.1.0-SNAPSHOT



## Copyright and License

The MIT License (MIT)

Copyright © 2014 Stuart Sierra

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
