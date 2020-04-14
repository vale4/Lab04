package it.polito.tdp.lab04;

import java.util.Comparator;

import it.polito.tdp.lab04.model.Corso;

public class ComparatoreCorsiNome implements Comparator<Corso> {

	@Override
	public int compare(Corso c1, Corso c2) {
		// TODO Auto-generated method stub
		return c1.getNome().compareTo(c2.getNome());
	}

}
