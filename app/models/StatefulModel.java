package models;

import play.libs.F;

public class StatefulModel {

   public static StatefulModel instance = new StatefulModel();
   
   public final F.EventStream event = new F.EventStream();
   
   private StatefulModel() { }
   
}
