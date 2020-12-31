package controllers.api
 
import javax.inject._
import play.api.mvc._
import play.api.libs.json._
import models.{Etudiant, EtudiantForm}
import play.api.data.FormError
 
import services.EtudiantService
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
 
class EtudiantController @Inject()(
    cc: ControllerComponents,
    todoService: EtudiantService
) extends AbstractController(cc) {
 
    implicit val EtdudiantFormat = Json.format[ETudiant]
 
    def getAll() = Action.async { implicit request: Request[AnyContent] =>
        EtudiantService.listAllItems map { items =>
          Ok(Json.toJson(items))
        }
      }
     
      def getById(INEtudiant: String) = Action.async { implicit request: Request[AnyContent] =>
        todoService.getItem(INEEtudiant) map { item =>
          Ok(Json.toJson(item))
        }
      }
     
      def add() = Action.async { implicit request: Request[AnyContent] =>
        EtudiantForm.form.bindFromRequest.fold(
          // if any error in submitted data
          errorForm => {
            errorForm.errors.foreach(println)
            Future.successful(BadRequest("Error!"))
          },
          data => {
            val newEtudiantItem = Etudiant(0, data.Nom, data.Prenom, data.Telephone)
            etudiantService.addItem(newEtudiantItem).map( _ => Redirect(routes.EtudiantController.getAll))
          })
      }
     
      def update(INEEtudiant: String) = Action.async { implicit request: Request[AnyContent] =>
        EtduiantForm.form.bindFromRequest.fold(
          // if any error in submitted data
          errorForm => {
            errorForm.errors.foreach(println)
            Future.successful(BadRequest("Error!"))
          },
          data => {
            val etudiantItem = Etudiant(INEEtudiant, data.Nom, data.Prenom, data.Telephone)
            etudiantService.updateItem(etudiantItem).map( _ => Redirect(routes.EtudiantController.getAll))
          })
      }
     
      def delete(INEEtudiant: String) = Action.async { implicit request: Request[AnyContent] =>
        etudiantService.deleteItem(INEEtudiant) map { res =>
          Redirect(routes.EtudiantController.getAll)
        }
      }
}