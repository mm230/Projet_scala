package services
 
import com.google.inject.Inject
import models.{Etudiant, EtudiantList}
 
import scala.concurrent.Future
 
class EtudiantService @Inject() (items: EtudiantList) {
 
  def addItem(item: Etudiant): Future[String] = {
    items.add(item)
  }
 
  def deleteItem(INEEtudiant: Long): Future[Int] = {
    items.delete(INEETUDIANT)
  }
 
  def updateItem(item: Etudiant): Future[Int] = {
    items.update(item)
  }
 
  def getItem(INEETUDIANT: Long): Future[Option[Etudiant]] = {
    items.get(INEETUDIANT)
  }
 
  def listAllItems: Future[Seq[Etudiant]] = {
    items.listAll
  }
}