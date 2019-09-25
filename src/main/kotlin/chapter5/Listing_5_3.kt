package chapter5

import chapter3.List
import chapter4.Option
import chapter5.Stream.Companion.cons
import chapter5.Stream.Companion.empty
import chapter3.Cons as ConsL
import chapter3.Nil as NilL

object Listing_5_3 {

    fun <A, B> Stream<A>.map(f: (A) -> B): Stream<B> = TODO()

    fun <A> Stream<A>.filter(f: (A) -> Boolean): Stream<A> = TODO()

    fun <A> Stream<A>.toList(): List<A> = TODO()

    fun <A> Stream<A>.headOption(): Option<A> = TODO()

    //tag::exists1[]
    fun <A> Stream<A>.exists(p: (A) -> Boolean): Boolean =
            when (this) {
                is Cons -> p(this.h()) || this.t().exists(p)
                else -> false
            }
    //end::exists1[]

    //tag::foldright[]
    fun <A, B> Stream<A>.foldRight(z: () -> B, f: (A, () -> B) -> B): B = // <1>
            when (this) {
                is Cons -> f(this.h(), { t().foldRight(z, f) }) // <2>
                else -> z()
            }
    //end::foldright[]

    //tag::exists2[]
    fun <A> Stream<A>.exists2(p: (A) -> Boolean): Boolean =
            foldRight({ false }, { a, b -> p(a) || b() })
    //end::exists2[]

    val listing = {
        //tag::trace[]
        Stream.of(1, 2, 3, 4).map { it + 10 }.filter { it % 2 == 0 }.toList()

        cons({ 11 }, { Stream.of(2, 3, 4) }).filter { it % 2 == 0 }.toList() // <1>

        Stream.of(2, 3, 4).map { it + 10 }.filter { it % 2 == 0 }.toList() // <2>

        cons({ 12 }, { Stream.of(3, 4) }).filter { it % 2 == 0 }.toList() // <3>

        ConsL(12, Stream.of(3, 4).map { it + 10 }.filter { it % 2 == 0 }.toList()) // <4>

        ConsL(12, cons({ 13 }, { Stream.of(4) }).filter { it % 2 == 0 }.toList())

        ConsL(12, Stream.of(4).map { it + 10 }.filter { it % 2 == 0 }.toList()) // <5>

        ConsL(12, ConsL(14, Stream.empty<Int>().map { it + 10 }.filter { it % 2 == 0 }.toList())) // <6>

        ConsL(12, ConsL(14, NilL)) // <7>
        //end::trace[]
    }

    //tag::find[]
    fun <A> Stream<A>.find(p: (A) -> Boolean): Option<A> =
            filter(p).headOption()
    //end::find[]
}