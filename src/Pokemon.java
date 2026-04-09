

public class Pokemon {
	private String nom;
	private String tipus;
	private int nivell;
	private int ps;
	private boolean capturat;
	
	public Pokemon(String nom, String tipus, String nivellStr, String psStr ,String capturat) throws DadesPokemonException {
        setNom(nom);
        setTipus(tipus);
        setNivell(nivellStr);
        setPs(psStr);
        setCapturat(capturat);
    }
	
	private void setNom(String nom) throws DadesPokemonException {
		if (nom == null || nom.trim().isEmpty()) {
            throw new DadesPokemonException("El nom no pot estar buit.");
        }
        this.nom = nom.trim();	
	}


	private void setTipus(String tipus) throws DadesPokemonException {
		if (tipus == null || tipus.trim().isEmpty()) {
			throw new DadesPokemonException("El tipus no pot estar buit");
		}
		this.tipus = tipus;
	}


	private void setNivell(String nivellStr) throws DadesPokemonException {
        try {
            int nivell = Integer.parseInt(nivellStr.trim());
            if (nivell < 1) {
                throw new DadesPokemonException("El nivell han de ser superiors a 0.");
            }
            this.nivell = nivell;
        } catch (NumberFormatException e) {
            throw new DadesPokemonException("El nivell han de ser números vàlids.");
        }
    }

	private void setPs(String psStr) throws DadesPokemonException {
        try {
            int ps = Integer.parseInt(psStr.trim());

            if (ps < 1) {
                throw new DadesPokemonException("El PS han de ser superiors a 0.");
            }
            this.ps = ps;
        } catch (NumberFormatException e) {
            throw new DadesPokemonException("El PS han de ser números vàlids.");
        }
    }

	private void setCapturat(String capturat) throws DadesPokemonException {
		if (capturat.equalsIgnoreCase("true")) {
			this.capturat = true;
		}else if(capturat.equalsIgnoreCase("false")) {		
			this.capturat = false;
		}else {
			throw new DadesPokemonException("Valor de capturat invalid");
		}
	}


	public String getNom() { return nom; }

	public String getTipus() { return tipus; }

	public int getNivell() { return nivell; }

	public int getPs() { return ps; }

	public boolean isCapturat() { return capturat; }
	
}
