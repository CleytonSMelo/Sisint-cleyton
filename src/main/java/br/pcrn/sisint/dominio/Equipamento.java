package br.pcrn.sisint.dominio;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

import br.pcrn.sisint.dominio.Manutencao;

/**
 * Created by samue on 09/09/2017.
 */
@Entity
public class Equipamento extends Entidade{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private Long tombo;
    private String numeroSerie;
    private LocalDate dataCadastro;
    private TipoEquipamento tipo;
    private boolean deletado;

    @Column(columnDefinition  = "text")
    private String descricao;

    @Enumerated(EnumType.STRING)
    private StatusEquipamento status;

    @ManyToOne(fetch = FetchType.EAGER)
    private Setor setor;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Manutencao> manutencoes;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Termo> termos;

    @Column(name ="log_equipamento")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<LogEquipamento> logEquipamentos;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) { this.nome = nome; }

    public String getNome() { return nome; }

    public Long getTombo() {
        return tombo;
    }

    public void setTombo(Long tombo) {
        this.tombo = tombo;
    }

    public String getDescricao() { return descricao; }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public Setor getSetor() { return setor; }

    public void setSetor(Setor setor) { this.setor = setor; }

    public boolean isDeletado() {
        return deletado;
    }

    public void setDeletado(boolean deletado) {
        this.deletado = deletado;
    }

    public List<Manutencao> getManutencoes() {
        return manutencoes;
    }

    public void setManutencoes(List<Manutencao> manutencoes) {
        this.manutencoes = manutencoes;
    }

    public StatusEquipamento getStatus() {  return status; }

    public void setStatus(StatusEquipamento status) { this.status = status; }

    public TipoEquipamento getTipo() {
        return tipo;
    }

    public void setTipo(TipoEquipamento tipo) {
        this.tipo = tipo;
    }

    public List<Termo> getTermos() {
        return termos;
    }

    public void setTermos(List<Termo> termos) {
        this.termos = termos;
    }

    public List<LogEquipamento> getLogEquipamentos() {
        return logEquipamentos;
    }

    public void setLogEquipamentos(List<LogEquipamento> logEquipamentos) {
        this.logEquipamentos = logEquipamentos;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
}
