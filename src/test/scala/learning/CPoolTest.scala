package com.danicabrown.learning

import java.sql.Connection
import org.scalatest._

final class CPoolTest extends FunSuite with BeforeAndAfterAll {
    override def afterAll() {
        CPool.shutdown
    }

    test("Get and release connections back to the pool.") {
        val a: Connection = CPool.getConnection
        val b: Connection = CPool.getConnection
        a.close
        val c: Connection = CPool.getConnection
        b.close
        c.close
    }

    test("Try to get more connections than the pool has.") {
        // The pool is configured for only 2 connections.
        CPool.getConnection
        CPool.getConnection
        // Requested a third connection, will result in a timeout.
        assertThrows[java.sql.SQLTimeoutException] {
            CPool.getConnection
        }
    }

    test("Cannot retrieve connections when the pool is shutdown.") {
        CPool.shutdown
        assertThrows[java.sql.SQLException] {
            CPool.getConnection
        }
    }
}
