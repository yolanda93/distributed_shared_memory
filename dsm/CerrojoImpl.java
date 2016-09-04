package dsm;
import java.io.IOException;
import java.rmi.*;
import java.rmi.server.*;

class CerrojoImpl extends UnicastRemoteObject implements Cerrojo {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int nlectores=0;
	private boolean HayEscritor=false;
	boolean cerrojoAbierto=true;
	CerrojoImpl() throws RemoteException {
    }
    
	// especifica si un acceso exclusivo o compartido.
    public synchronized void adquirir (boolean exc) throws RemoteException {
    	//permite multiples lectores pero un unico escritor.
    	cerrojoAbierto=false;
    	while(true) {
    	if(exc==true){ // quiere entrar un escritor.
    		if(nlectores>0 || HayEscritor) {// hay lectores o algun escritor ya tendra que esperar.
    		try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		}else{ // no espera
		    //System.out.println("aqui ponemos el escritor a true");
    	     HayEscritor=true;
    		 break;
    		}
    	}
    	else{// quiere entrar un lector
    	 if	(HayEscritor){ // pero si hay algun escritor no puede leer.
    		 try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	 }else{ // si no hay nadie escribiendo entonces si puede leer.
    		nlectores++; 
    		break;
    	 }
    		 
    	}
    		
    	}
    	
    }
    public synchronized boolean liberar() throws RemoteException {
    	if(cerrojoAbierto==true){
    		throw new RemoteException("No hay ningun cerrojo para liberar");
    	}
    		if(nlectores==0) { // hay un escritor ya que no hay lectores y esta el cerrojo.
			HayEscritor=false;
			notifyAll();
			return true;
		} else { // hay algun lector
			nlectores--;
			if (nlectores==0) {
				notifyAll();
				return true;
			}
		}
		return true;
    }
   
}
