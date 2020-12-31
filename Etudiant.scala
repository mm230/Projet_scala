package models<br>import com.google.inject.Inject
import play.api.data.Form
import play.api.data.Forms.mapping
import play.api.data.Forms._
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import scala.concurrent.{ExecutionContext, Future}
import slick.jdbc.MySQLProfile.api._
 
case class Etudiant(INEEtudiant: Long, Nom: String, Prenom: String)
 
case class EtudiantFormData(Nom: String, Prenom: String, Telephone: Int)
 
object EtudiantForm {
  val form = Form(
    mapping(
      "INEETUDIANT" -> nonEmptyText,
      "Nom" -> nonEmptyText,
      "Prenom" -> nonEmptyText,
      "Telephone" -> nonEmptyText,
    )(EtudiantFormData.apply)(EtudiantFormData.unapply)
  )
}
 
class EtudiantTableDef(tag: Tag) extends Table[Etudiant](tag, "etudiant") {
 
  def INEEtudiant = column[String]("INEEtudiant", O.PrimaryKey)
  def Nom = column[String]("Nom")
  def Prenom = column[String]("Prenom")
 
  override def * = (INEETUDIANT, Nom, Prenom) <> ((Etudiant.apply _).tupled, Etudiant.unapply _)
}
 
 
class EtudiantList @Inject()(
    protected val dbConfigProvider: DatabaseConfigProvider
)(implicit executionContext: ExecutionContext)
    extends HasDatabaseConfigProvider[JdbcProfile] {
 
  var EtudiantList = TableQuery[EtudiantTableDef]
 
  def add(EtudiantItem: Etudiant): Future[String] = {
    dbConfig.db
      .run(EtudiantList += EtudiantItem)
      .map(res => "EtudiantItem successfully added")
      .recover {
        case ex: Exception => {
            printf(ex.getMessage())
            ex.getMessage
        }
      }
  }
 
  def delete(INEEtudiant: String): Future[Int] = {
    dbConfig.db.run(etudiantList.filter(_.INEEtudiant === INEEtudiant).delete)
  }
 
  def update(etdudiantItem: Etudiant): Future[Int] = {
    dbConfig.db
      .run(etudiantList.filter(_.ineetudiant === etudiantItem.ineetudiant)
            .map(x => (x.Nom, x.Telephone))
            .update(etudiantItem.Nom, etudiantItem.Telephone)
      )
  }
 
  def get(INEEtudiant: String): Future[Option[Etudiant]] = {
    dbConfig.db.run(etudiantList.filter(_.INEEtudiant === INEEtudiant).result.headOption)
  }
 
  def listAll: Future[Seq[Etudiant]] = {
    dbConfig.db.run(etudiantList.result)
  }
}