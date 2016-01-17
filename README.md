# Being lightweight using ClojureScript

## TL;DR

A simple weight tracking application that I developed to learn [ClojureScript](https://github.com/clojure/clojurescript), [Datomic](http://www.datomic.com) , and the [re-frame](https://github.com/Day8/re-frame) 'framework'.
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

I initially looked at Om which seems a little ~~complicated~~hard to learn 
whilst picking up CLJS, [Figwheel](https://github.com/bhauman/lein-figwheel), 
etc. I then started looking at 
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
[src/cljs/paul_reduce/views.cljs](../src/cljs/paul_reduce/views.cljs) to 
understand how React.JS components are rendered using simple CLJS functions one 
way bound to subscriptions, and raise reagent events.
2. review 
[src/cljs/paul_reduce/handers.cljs](../src/cljs/paul_reduce/handers.cljs) to 
understand event handlers swap! the app-db atom implicitly
3. review [src/cljs/paul_reduce/subs.cljs](../src/cljs/paul_reduce/subs.cljs) to
 view subscriptions - view delegates of app-db allowing the view React.JS 
 components render when needed, supplied with data in the most helpful way. 
4. To further understand this, when starting using figwheel, enter 
`@re-frame.db/app-db` frequently in the REPl to see how app-db changes after 
adding weights. 

## Want to play with Datomic?

// TODO
