package com.example.codepathmail

import com.codepath.articlesearch.Wishlist

class WishlistFetcher {
    companion object {
        var senders = ""
        var title = ""
        var summary = ""
        fun getEmails(): MutableList<Wishlist> {
            var emails : MutableList<Wishlist> = ArrayList()
            for (i in 0..9) {

            }
            return emails
        }

        fun getNext5Emails(): MutableList<Wishlist> {
            var newEmails : MutableList<Wishlist> = ArrayList()
            for (i in 10..14) {
                val email = Wishlist(senders, title, summary)
                newEmails.add(email)
            }
            return newEmails
        }
        fun setEmails(i : String, l : String, q : String): MutableList<Wishlist> {
            var newEmails : MutableList<Wishlist> = ArrayList()
            senders= i
            title= l
            summary=q
            val email = Wishlist(senders, title, summary)
            newEmails.add(email)
            return newEmails
        }
    }
}