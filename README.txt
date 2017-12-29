Atlassian Stride coding exercise
Thomas Anderson 2017-12-29

OVERVIEW
--------
At Amazon we use a Web Services framework called CoralService that does all the heavy lifting of setting up our web services, and a code management and build framework call BrazilBuildSystem.
Suffice to say, I'm spoiled in that I don't have to concern myself with the intricacies of setting up a REST service.

So upon starting the coding exercise and seeing that I would need to do just that without my precious crutches,
I went and imported the git project used in https://spring.io/guides/gs/rest-service/ since it was clear to me that
there was no way I was going to be able to manually wire up Spring, TomCat, and any other dependencies into a
working build AND still have time to implement business logic and tests.

I took about two hours as instructed to set up the Spring project and put together the source and test code - I probably went over a bit making sure the unit tests were doing what I wanted and tweaking the regexes accordingly.

However, I did have to leave and come back later to write up this README and publish to GitHub - I hope that's ok.


LIBRARIES
---------
As mentioned above, I used Spring's REST service project to spin this up, which means pulling in elements of the
org.springframework.boot library.
I work with Spring regularly, so it's in my comfort zone for launcher and injection capabilities.

I also pulled in Lombok.
I have heard there are those who consider Lombok to be hacky.
Man, I sure hope you're not in that camp because I love clean, shiny code.


HOW TO RUN
----------
My code can be downloaded from https://github.com/arconet1/chat-metadata-service
or clone it:
  git clone https://github.com/arconet1/chat-metadata-service.git

It uses a Gradle wrapper to build, so once you download, cd into chat-metadata-service/ and build with:
  ./gradlew build

Then run the jar:
  java -jar build/libs/chat-metadata-service.jar
  
The service will be running locally on port 8080.
It can be called like this:
  http://localhost:8080/chatmetadata?chatString=@bob%20@john%20(success)%20such%20a%20cool%20feature;%20https://atlassian.com
  
The above results in the following output:
  {"mentions":["bob"],"emoticons":["success"],"urls":[{"url":"https://atlassian.com","title":"Atlassian | Software Development and Collaboration Tools"}]}


DEFICIENCIES
------------
The service does not pretty-print the JSON output.
I know this can be done by overriding some of the Jackson mapper config, but I did not get to it.

I did not know how to suppress null fields when mapping from a class, i.e. suppressing "emoticons" in the output if there
were none found in the input. I suppose I could do this if I were manually constructing the JSON string rather than marshalling
it from a class (ChatMetadata, in this case) but I would like to believe there's a way to have both. So my code just displays
empty arrays if doesn't find any mentions, emoticons, or urls.

The testing is pretty minimal. I tried to think of some good edge cases - parseable items at the beginning/end of the input,
items embedded in other items, items with disallowed characters, etc. Still, there's a lot more I could do, e.g. multi-line inputs.

A particular deficiency that is highlighted by the lack of unit tests is the url parsing. For the sake of time, I just went with a
simple regex that looks for anything starting with "http", but that's clearly not good enough and should be robust enough to
effectively validate urls.


WHAT ELSE I WOULD DO GIVEN MORE TIME
------------------------------------
Fix all the stuff in DEFICIENCIES.

The Url class makes an attempt to fetch the title from the given url. If it fails for any reason, it ignores the failure
and returns null. I think that behavior is fine, but going forward I would want to include some logging and metrics to track
such occurrences since I'll want to know if they're being caused by forces outside our control (e.g. access denied) or if they
point to a bug in the regex or business logic.

Custom error handling - there's no explicit mapping for /error so right now service errors fall back to a generic page with
no associated error messages.


