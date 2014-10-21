package br.com.imhotep.comparador;

import java.util.Comparator;

import br.com.imhotep.entidade.extra.MediaConsumoFarmacia;

public class MediaConsumoFarmaciaComparador implements Comparator<MediaConsumoFarmacia>  {  
	@Override
	public int compare(MediaConsumoFarmacia arg0, MediaConsumoFarmacia arg1) {
		return arg1.getMediaConsumo().compareTo(arg0.getMediaConsumo());
	}
}  
