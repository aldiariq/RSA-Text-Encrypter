
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author aldiariq
 */
public final class Enkripsi {
    private final int PanjangBit = 1024;
    private final Random rand = new Random();
    
    private BigInteger p;
    private BigInteger q;
    private BigInteger n;
    private BigInteger tn;
    private BigInteger e;
    private BigInteger d;
    
    private String LokasiFileDekripsi;
    
    Enkripsi(File fileasli) throws FileNotFoundException, IOException{
        this.setP(BigInteger.probablePrime(PanjangBit, rand));
        this.setQ(BigInteger.probablePrime(PanjangBit, rand));
        this.setN(p.multiply(q));
        this.setTn(p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE)));
        this.setE(this.generateE(this.getTn()));
        this.setD(this.generateD(this.getE(), this.getTn()));
        
        //Proses Pengambilan Byte File Asli
        File fFileasli = new File(fileasli.getAbsoluteFile().toString());
        FileInputStream fisFileasli = new FileInputStream(fFileasli);
        long panjangBytefileasli = fFileasli.length();

        byte[] byteFileasli = new byte[(int) panjangBytefileasli];
        fisFileasli.read(byteFileasli, 0, (int) panjangBytefileasli);
        
        //Proses Pembuatan File Terenkripsi
        byte[] byteEnkripsifile = prosesEnkripsi(this.getE(), this.getN(), byteFileasli);
        Path pathFileenkripsi = Paths.get("FolderOperasi/HasilEnkripsi/" + fileasli.getName() + ".enc");
        Files.write(pathFileenkripsi, byteEnkripsifile);
    }

    //Get Nilai P
    public BigInteger getP() {
        return p;
    }

    //Set Nilai P
    public void setP(BigInteger p) {
        this.p = p;
    }

    //Get Nilai Q
    public BigInteger getQ() {
        return q;
    }

    //Set Nilai Q
    public void setQ(BigInteger q) {
        this.q = q;
    }

    //Get Nilai N
    public BigInteger getN() {
        return n;
    }

    //Set Nilai N
    public void setN(BigInteger n) {
        this.n = n;
    }

    //Get Nilai TN
    public BigInteger getTn() {
        return tn;
    }

    //Set Nilai TN
    public void setTn(BigInteger tn) {
        this.tn = tn;
    }

    //Get Nilai E
    public BigInteger getE() {
        return e;
    }

    //Set Nilai E
    public void setE(BigInteger e) {
        this.e = e;
    }

    //Get Nilai D
    public BigInteger getD() {
        return d;
    }

    //Set Nilai D
    public void setD(BigInteger d) {
        this.d = d;
    }

    //Get Lokasi File Hasil Dekripsi
    public String getLokasiFileDekripsi() {
        return LokasiFileDekripsi;
    }

    //Set Lokasi File Hasil Dekripsi
    public void setLokasiFileDekripsi(String LokasiFileDekripsi) {
        this.LokasiFileDekripsi = LokasiFileDekripsi;
    }
    
    //Proses Mencari Nilai E
    private BigInteger generateE(BigInteger tn) {
        BigInteger gene;
        gene = BigInteger.probablePrime(PanjangBit, rand);
        do {
            gene = BigInteger.probablePrime(PanjangBit, rand);
        } while (tn.gcd(gene) == BigInteger.ONE);

        return gene;
    }
    
    //Proses Mencari Nilai D
    private BigInteger generateD(BigInteger e, BigInteger tn) {
        BigInteger gend;
        gend = e.modInverse(tn);
        return gend;
    }
    
    //Proses Enkripsi
    public static byte[] prosesEnkripsi(BigInteger e, BigInteger n, byte[] bytekalimat) {
        return (new BigInteger(bytekalimat)).modPow(e, n).toByteArray();
    }
}
