---
layout: post
title:  "Introduction to Etymologies Android app"
date:   2013-12-05 01:00:10
categories: jekyll update
---

The goal off the app is to make the process of learning etymologies as productive as possible.

To reach the goal we need a reliable data source and a crafted workflow. This data source for now is http://www.etymonline.com service. This service specializes in etymologies and for me is probaby the best starting point for learning about the word. In the future hopefully, I'll add other data sources, e.g. wiktionary.org or language specific etymology providers.

Core of the workflow is based around the search. Of course, Google (or some other search engine) can help you to learn about the words. The problem for me is that Google, in general, provides you links to the sites that are related to the search. In those links there is no guarantee that there will be any links that are related to etymologies. This problem, can be solved by crafting a search with additional keywords etc., but this is not a very good option if we are chasing for simplicity of use. And even with precise queries, there is a problem of a clutter in the search results, because you are not directly exposed to the results after clicking search - you're exposed to the links of the results. 

My idea is to postpone querying Google, by providing an app which is focused on searching solely for etymologies. So, I imagine that you start with the app, and if no luck, then you switch to Google or other heavyweight service of your preference. But if the app provides you results precisely with the answer for your query, this is a success. The problem with refined queries might be that the specific search might not find the precise answer, but maybe there are some related etymologies. I tackle this problem by putting few constraints on the query and providing all related results. This is fun when searching for broad term, like "soviet", then you have etymologies of many other related words like "Stalin", "Iron Curtain", and even "Nazi". It looks like kind of contextual search.

Search results comes with links to the definition of terms. Definitions are provided by dictionary.reference.com service. If in the results there is a word that has no link to the dictionary and you don't know what it means, you can select the word by long touch and and in the top right corner click on search icon - Google will help you.

What can you do with an etymology that you like? You can share it! In the action bar there is a share button that opens a standard Android dialog for sharing. You can share to the social networks, or just to your favourite note taking app. What is being shared, is the URL which opens the same results in the browser. You might ask, why not etymology itself? Because I don't want to have problems with intelectual property.

Another thing that you can do with etymologies is to check which etymologies you were searching in the past. Our memory is far from perfect, therefore, app is here to help. If you just can't remember what you've been searhing yesterday, just open the search history view.

To wrap up, my motivation to create this app is that for Android there are no focused tool for learning etymologies. Since I see great value in specialized tools, I decided to make one.