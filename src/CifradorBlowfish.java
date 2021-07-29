import java.util.Arrays;

public class CifradorBlowfish {
	private Palabra32bits[] digitosPI;
	private CajaS[] cajasS = null;
	private int modo;
	
	public static int MODO_ASCII = 0;
	public static int MODO_HEX = 1;
	
	final static int pbox_init[] = {
			
            0x243f6a88, 0x85a308d3, 0x13198a2e, 0x03707344, 0xa4093822, 0x299f31d0,
            0x082efa98, 0xec4e6c89, 0x452821e6, 0x38d01377, 0xbe5466cf, 0x34e90c6c,
            0xc0ac29b7, 0xc97c50dd, 0x3f84d5b5, 0xb5470917, 0x9216d5d9, 0x8979fb1b  };
	
	public CifradorBlowfish(int modo) {
		this.modo = modo;
		digitosPI = Palabra32bits.intToPalabra(pbox_init);
	}
	
	private Bloque cifrarBloque(Bloque bloque, Palabra32bits[] subclaves, CajaS[] cajasS) {
		Palabra32bits left = bloque.getLeft();
		Palabra32bits right = bloque.getRight();
		
		for(int i = 0; i < 16; i++) {
			left = left.xor(subclaves[i]);
			right = funcionF(left, cajasS).xor(right);
			left.intercambiar(right);
		}
		
		left.intercambiar(right);
		right = right.xor(subclaves[16]);
		left = left.xor(subclaves[17]);
		
		return new Bloque(left, right);
	}
	
	public String cifrar(String textoPlano, Palabra32bits[] clave) {
		Palabra32bits[] subclaves = generarSubclaves(clave);
		return cifrarConSubclaves(textoPlano, subclaves, this.cajasS);
	}
	
	public String descifrar(String textoCifrado, Palabra32bits[] clave) {
		Palabra32bits[] subclavesGeneradas = generarSubclaves(clave);
		Palabra32bits[] subclavesInvertidas = invertirClaves(subclavesGeneradas);
		
		return cifrarConSubclaves(textoCifrado, subclavesInvertidas, cajasS);
	}
	
	private Palabra32bits funcionF(Palabra32bits entrada, CajaS[] cajasS) {
		Palabra32bits resultado;
		resultado = cajasS[0].sustitucion(entrada.getByte(0))
				.sumar(cajasS[1].sustitucion(entrada.getByte(1)));
		resultado = resultado.xor(cajasS[2].sustitucion(entrada.getByte(2)));
		resultado = resultado.sumar(cajasS[3].sustitucion(entrada.getByte(3)));
		
		return resultado;
	}
	
	private Palabra32bits[] generarSubclaves(Palabra32bits[] clave) {
		Palabra32bits[] p = new Palabra32bits[18];
		for(int i = 0; i < p.length; i++)
			p[i] = getDigitoPI(i).xor(clave[i % clave.length]);
		
		CajaS[] cajasS = {new CajaS(1), new CajaS(2), new CajaS(3), new CajaS(4)};
		
		Bloque bloqueCifrado = Bloque.ALL_ZERO;
		
		for(int i = 0; i < p.length; i += 2) {
			bloqueCifrado = cifrarBloque(bloqueCifrado, p, cajasS);
			p[i] = bloqueCifrado.getLeft();
			p[i + 1] = bloqueCifrado.getRight();	
		}
		
		for(CajaS cajaS : cajasS) {
			for(int i = 0; i < 256; i += 2) {
				bloqueCifrado = cifrarBloque(bloqueCifrado, p, cajasS);
				cajaS.setPalabra(i, bloqueCifrado.getLeft());
				cajaS.setPalabra(i + 1, bloqueCifrado.getRight());
			}
		}
		
		this.cajasS = cajasS;
		return p;
		
	}
	
	public String cifrar(String textoPlano, int[] clave) {
		Palabra32bits[] claveBytes = Palabra32bits.intToPalabra(clave);
		return cifrar(textoPlano, claveBytes);
	}
	
	private String cifrarConSubclaves(String textoPlano, Palabra32bits[] subclaves, CajaS[] cajasS) {
		System.out.println("\nSubclaves generadas:\n" + Arrays.toString(subclaves) + "\n");
		
		Bloque[] bloques; 
		if(modo == MODO_ASCII)
			bloques = Bloque.getBloques(textoPlano);
		else
			bloques = Bloque.getHexBloques(textoPlano);
		
		Bloque[] bloquesCifrados = new Bloque[bloques.length];
		
		for(int i = 0; i < bloques.length; i++) {
			bloquesCifrados[i] = cifrarBloque(bloques[i], subclaves, cajasS);
		}
		
		if(modo == MODO_ASCII)
			return Bloque.toString(bloquesCifrados);
		else
			return Bloque.toHexString(bloquesCifrados);
	}
	
	public String descifrar(String textoCifrado, int[] clave) {
		Palabra32bits[] claveBytes = Palabra32bits.intToPalabra(clave);
		return descifrar(textoCifrado, claveBytes);
	}
	
	private Palabra32bits[] invertirClaves(Palabra32bits[] claves) {
		Palabra32bits[] clavesInvertidas = new Palabra32bits[claves.length];
		
		for(int i = 0; i < claves.length; i++) {
			clavesInvertidas[i] = claves[claves.length - i - 1];
		}
		
		return clavesInvertidas;
	}
	
	private Palabra32bits getDigitoPI(int indice) {
		return digitosPI[indice];
	}
	
	
	
	
}

