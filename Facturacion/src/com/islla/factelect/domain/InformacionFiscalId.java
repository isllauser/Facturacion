package com.islla.factelect.domain;


/**
 * La clase InformcaionFiscalId su finalidad es obtener el idInformacionFiscal del emisor
 */
public class InformacionFiscalId  implements java.io.Serializable {


     private int idInformacionFiscal;
     private String emisorRfcEmisor;

    public InformacionFiscalId() {
    }

    public InformacionFiscalId(int idInformacionFiscal, String emisorRfcEmisor) {
       this.idInformacionFiscal = idInformacionFiscal;
       this.emisorRfcEmisor = emisorRfcEmisor;
    }
   
    public int getIdInformacionFiscal() {
        return this.idInformacionFiscal;
    }
    
    public void setIdInformacionFiscal(int idInformacionFiscal) {
        this.idInformacionFiscal = idInformacionFiscal;
    }
    public String getEmisorRfcEmisor() {
        return this.emisorRfcEmisor;
    }
    
    public void setEmisorRfcEmisor(String emisorRfcEmisor) {
        this.emisorRfcEmisor = emisorRfcEmisor;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof InformacionFiscalId) ) return false;
		 InformacionFiscalId castOther = ( InformacionFiscalId ) other; 
         
		 return (this.getIdInformacionFiscal()==castOther.getIdInformacionFiscal())
 && ( (this.getEmisorRfcEmisor()==castOther.getEmisorRfcEmisor()) || ( this.getEmisorRfcEmisor()!=null && castOther.getEmisorRfcEmisor()!=null && this.getEmisorRfcEmisor().equals(castOther.getEmisorRfcEmisor()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + this.getIdInformacionFiscal();
         result = 37 * result + ( getEmisorRfcEmisor() == null ? 0 : this.getEmisorRfcEmisor().hashCode() );
         return result;
   }   


}


