package com.ping4.database.drivers

import com.github.tminglei.slickpg._
import slick.basic.Capability
import slick.driver.JdbcProfile
/*
trait PostgresDriverWithPostGisSupport extends ExPostgresProfile
                                        with PgArraySupport
                                        with PgHStoreSupport
                                        with PgSearchSupport
                                        with PgPostGISSupport {

  override lazy val Implicit = new ImplicitsPlus {}
  override val simple = new SimpleQLPlus {}

  trait ImplicitsPlus extends api
                      with ArrayImplicits
                      with HStoreImplicits
                      with SearchImplicits
                      with PostGISImplicits

  trait SimpleQLPlus extends SimpleQL
                      with ImplicitsPlus
                      with SearchAssistants
                      with PostGISAssistants
}

object PostgresDriverWithPostGisSupport extends PostgresDriverWithPostGisSupport
*/

trait PostgresDriverWithPostGisSupport extends ExPostgresProfile
  with PgArraySupport
  with PgHStoreSupport
  with PgSearchSupport
  with PgPostGISSupport {
  def pgjson = "jsonb" // jsonb support is in postgres 9.4.0 onward; for 9.3.x use "json"


  // Add back `capabilities.insertOrUpdate` to enable native `upsert` support; for postgres 9.5+
  override protected def computeCapabilities: Set[Capability] =
    super.computeCapabilities + JdbcProfile.capabilities.insertOrUpdate

  override val api = MyAPI

  object MyAPI extends API
    with ArrayImplicits
    with HStoreImplicits
    with SearchImplicits
    with PostGISImplicits
    with PostGISAssistants
    with SearchAssistants {
    implicit val strListTypeMapper = new SimpleArrayJdbcType[String]("text").to(_.toList)
  }
}

object PostgresDriverWithPostGisSupport extends PostgresDriverWithPostGisSupport

