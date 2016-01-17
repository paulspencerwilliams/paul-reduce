# Being lightweight using ClojureScript

A simple weight tracking application that I developed to learn [ClojureScript](https://github.com/clojure/clojurescript), [Datomic](http://www.datomic.com) , and the [re-frame](https://github.com/Day8/re-frame) 'framework'.

## TL;DR

To clone, compile/run, and play with the app using an in-memory Datomic db, 
then:
```
git clone git@github.com:paulspencerwilliams/paul-reduce.git
cd paul-reduce
lein figwheel
```

Browse to [http://localhost:3449](http://localhost:3449).

Start entering some date/weights...

## What's this all about then?

So, Clojure has been my 'go to' language for the last 4 years, but until mid 
2015, I've deliberately tried to avoid ClojureScript (CLJS). Why? Well, firstly,
 I don't agree with the client-side MVC by default approach to modern web dev.  
 Secondly, I felt that my limited extra-curricular time for learning Clojure and
  FP techniques etc would be insufficient to grok another language / toolchain 
  etc.
 
However, CLJS really started to gain traction in 2015 and I simply couldn't 
afford to ignore it.
 
Learning CLJS would be useless without understanding how to apply it in a 
typical web application, and thus I needed a relatively small, easy to 
understand problem domain with just enough requirements e.g. CRUD, reporting to 
make it worthwhile and sufficiently deep to see CLJS warts and all. As I'm also 
trying to lose weight, I thought a simple weight tracking/graphing application 
would be ideal and actually useful too. This is that application.  

I initially looked at [Om](https://github.com/omcljs/om) which seems a little 
~~complicated~~hard to learn whilst picking up CLJS, [Figwheel]
(https://github.com/bhauman/lein-figwheel), etc. I then started looking at 
[Reagent](https://github.com/reagent-project/reagent) which whilst seeming 
simpler, and more focused left lacking in terms of 'framework guidance' or and 
idiomatic way to build a simple app.

re-frame appears to be the CLJS 'framework' that best suited me. Batteries 
included, opinionated, and with an excellent 
[README](https://github.com/Day8/re-frame) that served as great introductory 
documentation. I like the single tree / atom app-db approach and how it supports 
events, and subscriptions expressively, and without noise.

To understand how this application works, I would recommend
 
1. starting in 
[src/cljs/paul_reduce/views.cljs](../master/src/cljs/paul_reduce/views.cljs) to 
understand how React.JS components are rendered using simple CLJS functions one 
way bound to subscriptions, and raise reagent events.
2. review 
[src/cljs/paul_reduce/handlers.cljs]
(../master/src/cljs/paul_reduce/handlers.cljs) to understand event handlers 
swap! the app-db atom implicitly
3. review 
[src/cljs/paul_reduce/subs.cljs](../master/src/cljs/paul_reduce/subs.cljs) to
 view subscriptions - view delegates of app-db allowing the view React.JS 
 components render when needed, supplied with data in the most helpful way. 
4. To further understand this, when starting using figwheel, enter 
`@re-frame.db/app-db` frequently in the REPl to see how app-db changes after 
adding weights. 

## Want to play with Datomic?

Datomic is an innovative database that supports time as a first class citizen. 
[Gigasquid](http://gigasquidsoftware.com) has put together, in my view, the best
 introduction to Datomic in her excellent Conversations with Datomic series 
(parts [1]
(http://gigasquidsoftware.com/blog/2015/08/15/conversations-with-datomic), [2]
(http://gigasquidsoftware.com/blog/2015/08/19/conversations-with-datomic-part-2)
, and [3]
(http://gigasquidsoftware.com/blog/2015/08/25/converstations-with-datomic-3)).
  
This datomic-pro branch of paul-reduce has a git commit 
[d377f18befc5987d52cf71520f15bb8a6cb97543]
(https://github.com/paulspencerwilliams/paul-reduce/commit/d377f18befc5987d52cf71520f15bb8a6cb97543)
 that reconfigures the application to use a Datomic Pro Starter instance on 
 the default port 4334.
 
To run this branch, download and follow the official [instructions]
(http://docs.datomic.com/getting-started.html) to get a 
Datomic Pro Starter running locally and then...

To run this application using the Datomic Pro Starter instance, follow the 
normal steps:

```
lein figwheel
```


When I presented this application at the Birmingham Functional Programming 
Meetup, I issued the following Datalog queries after recording a few weights 
in the application to view, and understand how Datomic stores, and replaces 
facts to support immutable persistence with time as a first class citizen:

Start a Clojure REPL
```
lein repl
```

Import the Datomic and supporting libraries
```
(require
  '[datomic.api :as d]
  '[datomic.query :as q])

(use '[clojure.string :only (join split)])
```

Connect to the Datomic Pro Starter instance:

```
(def uri "datomic:dev://localhost:4334/health-tracker")
(def conn (d/connect uri))
```

Pretty print the current recorded weights

```
(println (join  "\n" 
(d/q '[:find (pull ?e [*]) :where [?e :health/date]] (d/db conn))))
```

Pretty print all facts included those retracted to show how history is built up:
```
(println 
  (join  
    "\n" 
    (sort-by #(nth % 3)
    (d/q '[:find ?e ?a ?v ?tx ?op
       :in $
       :where [?e :health/date]
       [?e ?a ?v ?tx ?op]]
     (d/history (d/db conn))))))
```

Pretty print previous state of the database as of a previous transaction. 
Please replace XXXX with a previous transaction id from the previous query.
```
(println 
  (join  
    "\n" 
    (d/q 
      '[:find (pull ?e [*]) :where [?e :health/date]]
      (d/as-of (d/db conn) XXXX))))
```