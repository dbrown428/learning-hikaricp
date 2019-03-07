package com.danicabrown.learning

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import java.sql.Connection
import java.util.Properties
import org.postgresql.ds.PGSimpleDataSource

/**
 * Familiarizing myself with the Hikari connection pool using the Postgres 
 * Datasource class.
 */
object CPool {
    // Replace the hardcoded values with sys.env.get("POSTGRES_SERVER_NAME")
    private val datasourceProperties: Properties = {
        val props = new Properties()
        props.setProperty("dataSourceClassName", "org.postgresql.ds.PGSimpleDataSource");
        props.setProperty("dataSource.serverName", "localhost")
        props.setProperty("dataSource.portNumber", "54320")
        props.setProperty("dataSource.databaseName", "my_database")
        props.setProperty("dataSource.user", "my_username")
        props.setProperty("dataSource.password", "secret")
        props
    }

    // this can fail if properties are invalid. Don't care for this demo.
    private val config: HikariConfig = {   
        val c = new HikariConfig(datasourceProperties)
        
        /**
         * https://github.com/brettwooldridge/HikariCP/wiki/About-Pool-Sizing
         */
        c.setMaximumPoolSize(2)

        /**
         * This property controls the maximum lifetime of a connection in the 
         * pool. The Hikari team strongly recommends setting this value, and 
         * it should be several seconds shorter than any database or 
         * infrastructure imposed connection time limit.
         * 
         * Note: In milliseconds (ms)
         */
        c.setMaxLifetime(2000)

        /**
         * This property controls the maximum number of milliseconds that a 
         * client will wait for a connection from the pool. If this time is 
         * exceeded without a connection becoming available, a SQLException 
         * will be thrown.
         */
        c.setConnectionTimeout(1000)
        c
    }

    private val dataSource: HikariDataSource = new HikariDataSource(config)

    /**
     * Retrieve a connection from the pool.
     * @throws java.sql.SQLException - when the datasource has been shutdown.
     */
    def getConnection: Connection = dataSource.getConnection
    
    /**
     * Shutdown the entire pool when you're finished.
     */
    def shutdown: Unit = dataSource.close
}
