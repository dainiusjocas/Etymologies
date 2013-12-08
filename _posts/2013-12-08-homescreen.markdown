---
layout: post
title:  "Homescreen"
date:   2013-12-08 22:00:10
categories: jekyll update
---

Home screen of the app is the first thing a user sees when the app is opened. Current version of the homescreen is in the picture below.

<img src="{{ site.baseurl}}/images/homescreen.jpg" style="margin: auto; width: 50%;"/>

Homescreen has action bar, a picture of a sea-horse, and guide of the app. 

Action bar is the most important part of the homescreen. It has app icon, search icon, share icon, and an expandable menu.

To start exploring etymologies just tap on search icon. When tapped search field appears in the action bar, where you can enter the search term and enjoy learning etymologies.

Share button helps to share the fun of learning etymologies. In the picture you see yellow "Google Keep" icon. This is not default behaviour. This is the icon of last app which was used to share the content.

Expandable menu has four items:

* Home Page -- if for some reason you need a home page.
* Search history -- previews the list of recent searches (TODO).
* Setting -- some tweaks of the app (TODO).
* About -- some additional information about the app.

Remaining part of the window is a standard webview, that previews some html which is delivered with the application - do data downloads.

This html has a hack! The format of the picture is WebP. Why? Because the file is smaller due to advanced compression and since only Android webview is going to be used to preview the content there will be no issues about support of the format. Win-Win.