package ch.becompany.model

abstract sealed class Role(val id: String) {
  override def toString() = id
}
case object Corporal extends Role("corporal")
case object Sergeant extends Role("sergeant")
case object Captain extends Role("captain")
case object Commander extends Role("commander")

object Role {
  def parse(roleId: String): Role = Seq[Role](
    Corporal,
    Sergeant,
    Captain,
    Commander).find(_.id == roleId).getOrElse(
      throw new IllegalArgumentException(s"Unknown role $roleId"))
}