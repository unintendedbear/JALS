#!/usr/bin/ruby

=begin
    JALS is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    JALS is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with JALS. If not, see <http://www.gnu.org/licenses/>.
=end

require 'open-uri'
require 'rubygems'
require 'nokogiri'

URL_STRING = "https://www.port-monitor.com/plans-and-pricing"

class Scraper
  attr_accessor :url, :document, :elements

  def get_HTML_from_URL
    return Nokogiri::HTML(open(url))
  end

  def get_products
    return document.css("div[class='product']")
  end

  def get_products_in_hash
    elements.each do |product|
      puts product.css("h2").text
      puts product.css("dd")[0].text
      puts product.css("dd")[1].text
      puts product.css("dd")[2].text
      puts product.css("dd")[3].text
      puts product.css("a[class='btn btn-large btn-success']").text
    end
  end
end

jals = Scraper.new
jals.url = URL_STRING
jals.document = jals.get_HTML_from_URL()
jals.elements = jals.get_products()

jals.get_products_in_hash()
