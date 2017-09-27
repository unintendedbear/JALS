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
  attr_accessor :url, :document, :elements, :data

  def get_HTML_from_URL
    return Nokogiri::HTML(open(url))
  end

  def get_products
    return document.css("div[class='product']")
  end

  def get_products_in_hash
    data = Hash.new
    id = 0
    elements.each do |product|
      data[id] = Hash.new
      data[id]["monitors"] = product.css("h2").text
      data[id]["check_rate"] = product.css("dd")[0].text.gsub!(/\D/, "")
      data[id]["history"] = product.css("dd")[1].text.gsub!(/\D/, "")
      data[id]["multiple_notifications"] = product.css("dd")[2].text
      data[id]["push_notifications"] = product.css("dd")[3].text
      data[id]["price"] = product.css("a[class='btn btn-large btn-success']").text.gsub!(/^\$/, "").gsub!(/\s\/\s\w+/, "")
      id += 1
    end
    return data
  end

  def visualise_hash
    puts "[\n"
    data.keys.each do |id|
      puts "\t{"
      data[id].keys.each do |key|
        puts "\t\t#{key}:" + data[id][key]
      end
      puts "\t}"
    end
    puts "\n]"
  end
end

jals = Scraper.new
jals.url = URL_STRING
puts "JALS is now parsing https://www.port-monitor.com/plans-and-pricing"
jals.document = jals.get_HTML_from_URL()
jals.elements = jals.get_products()
jals.data = jals.get_products_in_hash()
puts "Your results:"
jals.visualise_hash()
puts "Thanks for using JALS!"
