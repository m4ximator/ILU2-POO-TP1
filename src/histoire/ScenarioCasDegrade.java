package histoire;
import personnages.Chef;
import personnages.Druide;
import personnages.Gaulois;
import villagegaulois.Etal;
import villagegaulois.Village;

public class ScenarioCasDegrade {

	public static void main(String[] args) {
		Etal etal=new Etal();
		Gaulois gaulois=new Gaulois("Exceptionnix",18);
//		System.out.println(etal.libererEtal());
//		System.out.println(etal.acheterProduit(10, null)); il faut aussi enlever la premiere exception dans acheterProduit
		
//		try {
//			etal.acheterProduit(-10,gaulois);
//		}catch(IllegalArgumentException e) {
//			 System.out.println("Exception attrapée : " + e.getMessage());
//		}
		
		try {
			etal.acheterProduit(10, gaulois);
		}catch(IllegalStateException e) {
			System.out.println("Exception attrapee: "+e.getMessage());
		}
		
		System.out.println("fin test");

	}

}
