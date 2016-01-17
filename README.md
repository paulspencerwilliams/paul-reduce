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
  
The master branch of paul-reduce uses the in-memory datomic-free which 
deliberately trades elimination of separate installation / configuration of 
Datomic, for the inability to remotely connect from a REPL and explore. This 
is sufficient to quickly get to grips with this application. 

However, if you really want to dive in a little deeper on how I used Datomic,
 I recommend switching to the datomic-pro branch where I reconfigure the 
 application to use a separately installed, local instance of Datomic. Please
 refer to the Readme in that branch for installation guidance, and the 
 cheat-sheet I used at the Birmingham FP meetup to time travel!
