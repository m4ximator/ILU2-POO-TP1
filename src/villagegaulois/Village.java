package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		marche = new Marche(nbEtals);
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() throws VillageSansChefException {
		if (chef == null)
			throw new VillageSansChefException();
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef " + chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom() + " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}

	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		StringBuilder chaine = new StringBuilder();
		chaine.append(vendeur.getNom() + " cherche un endroit pour vendre " + nbProduit + " " + produit + ".\n");
		int indiceNouvelEtal = marche.trouverEtalLibre();
		marche.utiliserEtal(indiceNouvelEtal, vendeur, produit, nbProduit);
		chaine.append("Le vendeur " + vendeur.getNom() + " vend des " + produit + " a l'etal numero " + indiceNouvelEtal
				+ ".");
		return chaine.toString();
	}

	public String rechercherVendeursProduit(String produit) {
	    StringBuilder chaine = new StringBuilder();
	    Etal[] etalsTrouves = marche.trouverEtals(produit);
	    if (etalsTrouves.length > 0) {
	        for (Etal etal : etalsTrouves) {
	            Gaulois vendeur = etal.getVendeur();
	            chaine.append("Vendeur : ").append(vendeur.getNom()).append(", Produit : ").append(produit).append("\n");
	        }
	    } else {
	        chaine.append("Aucun vendeur trouvé pour le produit : ").append(produit).append("\n");
	    }
	    return chaine.toString();
	}


	public Etal rechercherEtal(Gaulois vendeur) {
		return marche.trouverVendeur(vendeur);
	}

	public String partirVendeur(Gaulois vendeur) {
		Etal etalLibere = marche.trouverVendeur(vendeur);
		return etalLibere.libererEtal();
	}

	public String afficherMarche() {
		StringBuilder chaine = new StringBuilder();
		chaine.append("Le marche du village ").append(this.getNom()).append(" poosede plusieurs etals :\n")
				.append(marche.afficherMarche());
		return chaine.toString();
	}

	//
	private static class Marche {
		private Etal[] etals;

		private Marche(int nbEtal) {
			etals = new Etal[nbEtal];
		}

		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			etals[indiceEtal] = new Etal();
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}

		private int trouverEtalLibre() {
			for (int i = 0; i < etals.length; i++) {
				if (etals[i] == null || !etals[i].isEtalOccupe()) {
					return i;
				}
			}
			return -1;
		}

		private Etal[] trouverEtals(String produit) {
			int compteurEtal = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i] != null && etals[i].contientProduit(produit)) {
					compteurEtal++;
				}
			}
			Etal[] newEtal = new Etal[compteurEtal];
			int index = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i] != null && etals[i].contientProduit(produit)) {
					newEtal[index] = etals[i];
					index++;
				}
			}
			return newEtal;
		}

		private Etal trouverVendeur(Gaulois gaulois) {
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].getVendeur() == gaulois) {
					return etals[i];
				}
			}
			return null;
		}

		private String afficherMarche() {
			StringBuilder chaine = new StringBuilder();
			int nbEtalVide = 0;

			for (Etal etal : etals) {
				if (etal != null) {
					chaine.append(etal.afficherEtal()).append("\n");
				} else {
					nbEtalVide++;
				}
			}

			if (nbEtalVide > 0) {
				chaine.append("Il reste ").append(nbEtalVide).append(" etals non utilises dans le marche.\n");
			}

			return chaine.toString();
		}

	}
}