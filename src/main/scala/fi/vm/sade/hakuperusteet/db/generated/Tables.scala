package fi.vm.sade.hakuperusteet.db.generated
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.driver.PostgresDriver
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.driver.JdbcProfile
  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = SchemaVersion.schema ++ User.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table SchemaVersion
   *  @param versionRank Database column version_rank SqlType(int4)
   *  @param installedRank Database column installed_rank SqlType(int4)
   *  @param version Database column version SqlType(varchar), PrimaryKey, Length(50,true)
   *  @param description Database column description SqlType(varchar), Length(200,true)
   *  @param `type` Database column type SqlType(varchar), Length(20,true)
   *  @param script Database column script SqlType(varchar), Length(1000,true)
   *  @param checksum Database column checksum SqlType(int4), Default(None)
   *  @param installedBy Database column installed_by SqlType(varchar), Length(100,true)
   *  @param installedOn Database column installed_on SqlType(timestamp)
   *  @param executionTime Database column execution_time SqlType(int4)
   *  @param success Database column success SqlType(bool) */
  case class SchemaVersionRow(versionRank: Int, installedRank: Int, version: String, description: String, `type`: String, script: String, checksum: Option[Int] = None, installedBy: String, installedOn: java.sql.Timestamp, executionTime: Int, success: Boolean)
  /** GetResult implicit for fetching SchemaVersionRow objects using plain SQL queries */
  implicit def GetResultSchemaVersionRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Option[Int]], e3: GR[java.sql.Timestamp], e4: GR[Boolean]): GR[SchemaVersionRow] = GR{
    prs => import prs._
    SchemaVersionRow.tupled((<<[Int], <<[Int], <<[String], <<[String], <<[String], <<[String], <<?[Int], <<[String], <<[java.sql.Timestamp], <<[Int], <<[Boolean]))
  }
  /** Table description of table schema_version. Objects of this class serve as prototypes for rows in queries.
   *  NOTE: The following names collided with Scala keywords and were escaped: type */
  class SchemaVersion(_tableTag: Tag) extends Table[SchemaVersionRow](_tableTag, Some("hakuperusteet"), "schema_version") {
    def * = (versionRank, installedRank, version, description, `type`, script, checksum, installedBy, installedOn, executionTime, success) <> (SchemaVersionRow.tupled, SchemaVersionRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(versionRank), Rep.Some(installedRank), Rep.Some(version), Rep.Some(description), Rep.Some(`type`), Rep.Some(script), checksum, Rep.Some(installedBy), Rep.Some(installedOn), Rep.Some(executionTime), Rep.Some(success)).shaped.<>({r=>import r._; _1.map(_=> SchemaVersionRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7, _8.get, _9.get, _10.get, _11.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column version_rank SqlType(int4) */
    val versionRank: Rep[Int] = column[Int]("version_rank")
    /** Database column installed_rank SqlType(int4) */
    val installedRank: Rep[Int] = column[Int]("installed_rank")
    /** Database column version SqlType(varchar), PrimaryKey, Length(50,true) */
    val version: Rep[String] = column[String]("version", O.PrimaryKey, O.Length(50,varying=true))
    /** Database column description SqlType(varchar), Length(200,true) */
    val description: Rep[String] = column[String]("description", O.Length(200,varying=true))
    /** Database column type SqlType(varchar), Length(20,true)
     *  NOTE: The name was escaped because it collided with a Scala keyword. */
    val `type`: Rep[String] = column[String]("type", O.Length(20,varying=true))
    /** Database column script SqlType(varchar), Length(1000,true) */
    val script: Rep[String] = column[String]("script", O.Length(1000,varying=true))
    /** Database column checksum SqlType(int4), Default(None) */
    val checksum: Rep[Option[Int]] = column[Option[Int]]("checksum", O.Default(None))
    /** Database column installed_by SqlType(varchar), Length(100,true) */
    val installedBy: Rep[String] = column[String]("installed_by", O.Length(100,varying=true))
    /** Database column installed_on SqlType(timestamp) */
    val installedOn: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("installed_on")
    /** Database column execution_time SqlType(int4) */
    val executionTime: Rep[Int] = column[Int]("execution_time")
    /** Database column success SqlType(bool) */
    val success: Rep[Boolean] = column[Boolean]("success")

    /** Index over (installedRank) (database name schema_version_ir_idx) */
    val index1 = index("schema_version_ir_idx", installedRank)
    /** Index over (success) (database name schema_version_s_idx) */
    val index2 = index("schema_version_s_idx", success)
    /** Index over (versionRank) (database name schema_version_vr_idx) */
    val index3 = index("schema_version_vr_idx", versionRank)
  }
  /** Collection-like TableQuery object for table SchemaVersion */
  lazy val SchemaVersion = new TableQuery(tag => new SchemaVersion(tag))

  /** Entity class storing rows of table User
   *  @param id Database column id SqlType(serial), AutoInc, PrimaryKey
   *  @param henkiloOid Database column henkilo_oid SqlType(varchar), Length(255,true), Default(None)
   *  @param email Database column email SqlType(varchar), Length(255,true)
   *  @param idpentity Database column idpentity SqlType(varchar), Length(255,true)
   *  @param firstname Database column firstname SqlType(varchar), Length(255,true)
   *  @param lastname Database column lastname SqlType(varchar), Length(255,true)
   *  @param gender Database column gender SqlType(varchar), Length(255,true)
   *  @param birthdate Database column birthdate SqlType(date)
   *  @param personid Database column personid SqlType(varchar), Length(255,true), Default(None)
   *  @param nationality Database column nationality SqlType(varchar), Length(255,true)
   *  @param educationLevel Database column education_level SqlType(varchar), Length(255,true)
   *  @param educationCountry Database column education_country SqlType(varchar), Length(255,true) */
  case class UserRow(id: Int, henkiloOid: Option[String] = None, email: String, idpentity: String, firstname: String, lastname: String, gender: String, birthdate: java.sql.Date, personid: Option[String] = None, nationality: String, educationLevel: String, educationCountry: String)
  /** GetResult implicit for fetching UserRow objects using plain SQL queries */
  implicit def GetResultUserRow(implicit e0: GR[Int], e1: GR[Option[String]], e2: GR[String], e3: GR[java.sql.Date]): GR[UserRow] = GR{
    prs => import prs._
    UserRow.tupled((<<[Int], <<?[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[java.sql.Date], <<?[String], <<[String], <<[String], <<[String]))
  }
  /** Table description of table user. Objects of this class serve as prototypes for rows in queries. */
  class User(_tableTag: Tag) extends Table[UserRow](_tableTag, Some("hakuperusteet"), "user") {
    def * = (id, henkiloOid, email, idpentity, firstname, lastname, gender, birthdate, personid, nationality, educationLevel, educationCountry) <> (UserRow.tupled, UserRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), henkiloOid, Rep.Some(email), Rep.Some(idpentity), Rep.Some(firstname), Rep.Some(lastname), Rep.Some(gender), Rep.Some(birthdate), personid, Rep.Some(nationality), Rep.Some(educationLevel), Rep.Some(educationCountry)).shaped.<>({r=>import r._; _1.map(_=> UserRow.tupled((_1.get, _2, _3.get, _4.get, _5.get, _6.get, _7.get, _8.get, _9, _10.get, _11.get, _12.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(serial), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column henkilo_oid SqlType(varchar), Length(255,true), Default(None) */
    val henkiloOid: Rep[Option[String]] = column[Option[String]]("henkilo_oid", O.Length(255,varying=true), O.Default(None))
    /** Database column email SqlType(varchar), Length(255,true) */
    val email: Rep[String] = column[String]("email", O.Length(255,varying=true))
    /** Database column idpentity SqlType(varchar), Length(255,true) */
    val idpentity: Rep[String] = column[String]("idpentity", O.Length(255,varying=true))
    /** Database column firstname SqlType(varchar), Length(255,true) */
    val firstname: Rep[String] = column[String]("firstname", O.Length(255,varying=true))
    /** Database column lastname SqlType(varchar), Length(255,true) */
    val lastname: Rep[String] = column[String]("lastname", O.Length(255,varying=true))
    /** Database column gender SqlType(varchar), Length(255,true) */
    val gender: Rep[String] = column[String]("gender", O.Length(255,varying=true))
    /** Database column birthdate SqlType(date) */
    val birthdate: Rep[java.sql.Date] = column[java.sql.Date]("birthdate")
    /** Database column personid SqlType(varchar), Length(255,true), Default(None) */
    val personid: Rep[Option[String]] = column[Option[String]]("personid", O.Length(255,varying=true), O.Default(None))
    /** Database column nationality SqlType(varchar), Length(255,true) */
    val nationality: Rep[String] = column[String]("nationality", O.Length(255,varying=true))
    /** Database column education_level SqlType(varchar), Length(255,true) */
    val educationLevel: Rep[String] = column[String]("education_level", O.Length(255,varying=true))
    /** Database column education_country SqlType(varchar), Length(255,true) */
    val educationCountry: Rep[String] = column[String]("education_country", O.Length(255,varying=true))

    /** Uniqueness Index over (email) (database name user_email) */
    val index1 = index("user_email", email, unique=true)
  }
  /** Collection-like TableQuery object for table User */
  lazy val User = new TableQuery(tag => new User(tag))
}