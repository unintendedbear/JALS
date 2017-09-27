#!/usr/bin/ruby

require 'open-uri'
require 'rexml/document'
include REXML

URL_STRING = "https://www.port-monitor.com/plans-and-pricing"

class Scraper
  attr_accessor :url

  def get_HTML_from_URL
    url_document = open(url).read
    return url_document
  end
end

jals = Scraper.new
jals.url = URL_STRING
document = jals.get_HTML_from_URL()
puts document
