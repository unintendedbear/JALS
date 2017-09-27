# JALS
JALS ~ Just A Little Scraper

JALS is a little scraper that extracts for you the pricing information from https://www.port-monitor.com/plans-and-pricing.
It has been programmed in two languages: JAVA and Ruby

## JAVA execution

```
$ java -jar JAVA\ version/JALS.jar
```

## Ruby

In order to execute ```JALS.rb```, you have to install the Nokogiri gem by following [these instructions]{nokogiri.org/tutorials/installing_nokogiri.html}. For instance, for Ubuntu/Debian (which is the one I use):

```
$ sudo apt-get install build-essential patch ruby-dev zlib1g-dev liblzma-dev
$ gem install nokogiri
```

Then just:

```
$ ruby Ruby\ version/JALS.rb
```


Please leave an issue to report any bugs or improvements, or just make me a pull request!
Thanks for passing by :)
