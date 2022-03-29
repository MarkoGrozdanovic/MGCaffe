
package Model;

/**
 *
-TEMPLATE PATTERN-definiše strukturu (skelet) nekog algoritma, pri čemu se definicija određenih koraka delegira podklasama
                 - omogućava podklasama da redefinišu određene korake algoritma bez izmene strukture algoritma
 * 
 */
public abstract class GenericObject {
    public abstract String getClassName();
    public abstract String getColumns();
    public abstract String getValues();
    public abstract String makeWhere();
    public abstract String makeSet();
    
    //metoda za pravljenje insert-a 
    public String makeInsert() {
        return "INSERT INTO "+getClassName()+" ("+getColumns()+")"+"\n"
                +"VALUE ("+getValues()+")";
    }
    
    //metoda za pravljenje upita 
    public String makeSelect() {
        return "SELECT "+getColumns()+"\n"
                +"FROM "+getClassName()+"\n"
                +"WHERE 1 = 1 "+makeWhere();
    }
    
    //metoda za ažuriranje podataka u bazi
    public String makeUpdate(){
        return "UPDATE "+getClassName()+"\n"
                + "SET "+makeSet()+"\n"
                +"WHERE 1 = 1 "+makeWhere();
    }
}
