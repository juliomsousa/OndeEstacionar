package br.com.juliomsousa.ondeestacionar.model;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by juliomsousa on 28/05/17.
 */

public class Local implements Serializable{

    private Double latitude;
    private Double longitude;

    private String nome;
    private String endereco;
    private String numero;
    private String cidade;
    private Boolean ativo;

    private Double precoAte1Hora;
    private Double precoHoraAdicional;
    private Double preco6Horas;
    private Double precoDiaria;

    private Boolean vagasMensalista;
    private Boolean temSeguro;
    private Boolean temManobrista;
    private Double avaliacao;

    public Local() {
        this.latitude = null;
        this.longitude = null;
        this.nome = null;
        this.endereco = null;
        this.numero = null;
        this.cidade = null;
        this.ativo = false;
        this.precoAte1Hora = null;
        this.precoHoraAdicional = null;
        this.preco6Horas = null;
        this.precoDiaria = null;
        this.vagasMensalista = false;
        this.temSeguro = false;
        this.temManobrista = false;
        this.avaliacao = null;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public Boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Double getPrecoAte1Hora() {
        return precoAte1Hora;
    }

    public void setPrecoAte1Hora(Double precoAte1Hora) {
        this.precoAte1Hora = precoAte1Hora;
    }

    public Double getPrecoHoraAdicional() {
        return precoHoraAdicional;
    }

    public void setPrecoHoraAdicional(Double precoHoraAdicional) {
        this.precoHoraAdicional = precoHoraAdicional;
    }

    public Double getPreco6Horas() {
        return preco6Horas;
    }

    public void setPreco6Horas(Double preco6Horas) {
        this.preco6Horas = preco6Horas;
    }

    public Double getPrecoDiaria() {
        return precoDiaria;
    }

    public void setPrecoDiaria(Double precoDiaria) {
        this.precoDiaria = precoDiaria;
    }

    public Boolean getVagasMensalista() {
        return vagasMensalista;
    }

    public void setVagasMensalista(Boolean vagasMensalista) {
        this.vagasMensalista = vagasMensalista;
    }

    public Boolean getTemSeguro() {
        return temSeguro;
    }

    public void setTemSeguro(Boolean temSeguro) {
        this.temSeguro = temSeguro;
    }

    public Boolean getTemManobrista() {
        return temManobrista;
    }

    public void setTemManobrista(Boolean temManobrista) {
        this.temManobrista = temManobrista;
    }

    public Double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("latitude", getLatitude());
        result.put("longitude", getLongitude());

        result.put("nome", getNome());
        result.put("endereco", getEndereco());
        result.put("numero", getNumero());
        result.put("cidade", getCidade());
        result.put("ativo", isAtivo());

        result.put("precoAte1Hora", getPrecoAte1Hora());
        result.put("precoHoraAdicional", getPrecoHoraAdicional());
        result.put("preco6Horas", getPreco6Horas());
        result.put("precoDiaria", getPrecoDiaria());

        result.put("vagasMensalista", getVagasMensalista());
        result.put("temSeguro", getTemSeguro());
        result.put("temManobrista", getTemManobrista());

        return result;
    }

    @Override
    public String toString() {
        return "Local{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", nome='" + nome + '\'' +
                ", endereco='" + endereco + '\'' +
                ", numero='" + numero + '\'' +
                ", cidade='" + cidade + '\'' +
                ", ativo=" + ativo +
                ", precoAte1Hora=" + precoAte1Hora +
                ", precoHoraAdicional=" + precoHoraAdicional +
                ", preco6Horas=" + preco6Horas +
                ", precoDiaria=" + precoDiaria +
                ", vagasMensalista=" + vagasMensalista +
                ", temSeguro=" + temSeguro +
                ", temManobrista=" + temManobrista +
                ", avaliacao=" + avaliacao +
                '}';
    }
}