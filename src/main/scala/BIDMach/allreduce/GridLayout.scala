package BIDMach.allreduce

class GridGroup(val index: Integer, val dim: Integer){}

class GridLayout(val scale: Integer, val dim: Integer) {
  val total: Integer = scale * dim
  type GridMember = Array[Integer]
  private val members: Array[GridMember] = genMembers(dim)

  private def genMembers(dim: Integer): Array[Array[Integer]] =  {
    if(dim == 0){
      Array(Array())
    }
    else{
      var ret: Array[Array[Integer]] = Array()
      for(x <- genMembers(dim -1)){
        for(i <- 0 until scale){
          ret :+= (x:+i).asInstanceOf[Array[Integer]]
      }
      }
      ret
    }
  }

  def members(group: GridGroup):Array[Integer]={
    //given a group, return the members it have, O(total) naiive implemetation
    var ret = Array[Integer]()
    for((member, i) <- members.zipWithIndex){
      if(member(group.dim) == group.index){
        ret :+= i.asInstanceOf[Integer]
      }
    }
    ret
  }

  def groups(member_idx:Integer):Array[GridGroup]= {
    //given a member_idx, return the groups it belong to
    assert(0 <= member_idx && member_idx < total)
    var ret = Array[GridGroup]();
    val member = members(member_idx);
    for (i <- 0 until dim) {
      ret :+= new GridGroup(member(i), i)
    }
    ret
  }
}

