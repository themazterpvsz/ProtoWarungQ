package com.kretek.erab.managementwarung.model;

/**
 * Created by erab on 26/10/17.
 * Awali Code Mu dengan Bismillah
 * Biar makin okeoce++
 */

public class Laporan {

    String id;
    String namaLaporan;
    String jenisLaporan;
    String total;
    String hariInput;
    String lastEdit;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNamaLaporan() {
        return namaLaporan;
    }

    public void setNamaLaporan(String namaLaporan) {
        this.namaLaporan = namaLaporan;
    }

    public String getJenisLaporan() {
        return jenisLaporan;
    }

    public void setJenisLaporan(String jenisLaporan) {
        this.jenisLaporan = jenisLaporan;
    }

    public String getHariInput() {
        return hariInput;
    }

    public void setHariInput(String hariInput) {
        this.hariInput = hariInput;
    }

    public String getLastEdit() {
        return lastEdit;
    }

    public void setLastEdit(String lastEdit) {
        this.lastEdit = lastEdit;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
