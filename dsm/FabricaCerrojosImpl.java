package dsm;
import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

public class FabricaCerrojosImpl extends UnicastRemoteObject implements FabricaCerrojos {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Map<String, Cerrojo> almacenCerrojo = new HashMap<String, Cerrojo>();
	
    public FabricaCerrojosImpl() throws RemoteException {
    }
    
    public synchronized	Cerrojo iniciar(String s) throws RemoteException {
    	Cerrojo res;
		if (almacenCerrojo.containsKey(s)){
			res= almacenCerrojo.get(s);
		}
		else{
			res = new CerrojoImpl();
			almacenCerrojo.put(s, res);	
		}	
		return res;
    }
}

