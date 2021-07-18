
public class Main {

	public static void main(String[] args) {
		CifradorBlowfish blowfish = new CifradorBlowfish();
		String textoPlano = "1234567890";
		int[] clave = {1,2,3,4,5,6,7,8,9,10,11,12,13,15};
		
		String textoCifrado = blowfish.cifrar(textoPlano, clave);
		System.out.println(textoCifrado);
		System.out.println(blowfish.descifrar(textoCifrado, clave));
	}

}
