package renegade.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TokenPool {
    private List<Contaminant> blueGreenPool = new ArrayList<>();
    private List<Contaminant> redYellowPool = new ArrayList<>();
    private List<Contaminant> installations = new ArrayList<>();

    private List<Countermeasure> sparkFlarePool = new ArrayList<>();
    private List<Countermeasure> guardiansFirewalls = new ArrayList<>();

    public TokenPool(){
        for (int i = 0; i < 15; ++i){
            blueGreenPool.add(new Contaminant(ContaminantType.DATA_NODE));
            redYellowPool.add(new Contaminant(ContaminantType.VIRUS));
        }
        for (int i = 0; i < 25; ++i){
            sparkFlarePool.add(new Countermeasure(CountermeasureType.SPARK));
        }
        for (int i = 0; i < 5; ++i){
            guardiansFirewalls.add(new Countermeasure(CountermeasureType.GUARDIAN));
            installations.add(new Contaminant(ContaminantType.DATA_PORT));
            installations.add(new Contaminant(ContaminantType.NEURAL_HUB));
            installations.add(new Contaminant(ContaminantType.PROPAGATOR));
            installations.add(new Contaminant(ContaminantType.REPLICATOR));
        }
    }

    public Contaminant getDataNode(){
        return getContaminant(ContaminantType.DATA_NODE);
    }

    public Contaminant getUplink(){
        return getContaminant(ContaminantType.UPLINK);
    }

    public Contaminant getVirus(){
        return getContaminant(ContaminantType.VIRUS);
    }

    public Contaminant getReplicant(){
        return getContaminant(ContaminantType.REPLICANT);
    }

    public Contaminant getReplicator(){
        return getContaminant(ContaminantType.REPLICATOR);
    }

    public Contaminant getNeuralHub(){
        return getContaminant(ContaminantType.NEURAL_HUB);
    }

    public Contaminant getPropagator(){
        return getContaminant(ContaminantType.PROPAGATOR);
    }

    public Contaminant getDataPort(){
        return getContaminant(ContaminantType.DATA_PORT);
    }

    public Countermeasure getSpark(){
        return getCountermeasure(CountermeasureType.SPARK);
    }

    public Countermeasure getFlare(){
        return getCountermeasure(CountermeasureType.FLARE);
    }

    public Countermeasure getGuardian(){
        return getCountermeasure(CountermeasureType.GUARDIAN);
    }

    public Countermeasure getFirewall(){
        return getCountermeasure(CountermeasureType.FIREWALL);
    }

    public Contaminant getContaminant(ContaminantType type){
        if (type == ContaminantType.DATA_NODE || type == ContaminantType.UPLINK)
            return getBlueGreenContaminant(type);
        if (type == ContaminantType.VIRUS || type == ContaminantType.REPLICANT)
            return getRedYellowContaminant(type);
        Optional<Contaminant> opt = installations.stream().filter(i -> i.getType() == type).findFirst();
        if (opt.isPresent()) {
            installations.remove(opt.get());
            return opt.get();
        }
        return null;
    }

    public Contaminant getBlueGreenContaminant(ContaminantType type){
        if (blueGreenPool.isEmpty())
            return null;
        Contaminant contaminant = blueGreenPool.remove(0);
        if (contaminant.getType() != type)
            contaminant.flip();
        return contaminant;
    }

    public Contaminant getRedYellowContaminant(ContaminantType type){
        if (redYellowPool.isEmpty())
            return null;
        Contaminant contaminant = redYellowPool.remove(0);
        if (contaminant.getType() != type)
            contaminant.flip();
        return contaminant;
    }

    public Countermeasure getCountermeasure(CountermeasureType type){
        if (type == CountermeasureType.SPARK || type == CountermeasureType.FLARE){
            if (sparkFlarePool.isEmpty())
                return null;
            Countermeasure countermeasure = sparkFlarePool.remove(0);
            if (countermeasure.getType() != type)
                countermeasure.flip();
            return countermeasure;
        }
        Optional<Countermeasure> opt = guardiansFirewalls.stream().filter(gf -> gf.getType() == type).findFirst();
        if (opt.isPresent()){
            guardiansFirewalls.remove(opt.get());
            return opt.get();
        }
        return null;
    }

    public void returnToPool(Contaminant contaminant){
        if (contaminant.getType() == ContaminantType.DATA_NODE || contaminant.getType() == ContaminantType.UPLINK)
            blueGreenPool.add(contaminant);
        else if (contaminant.getType() == ContaminantType.VIRUS || contaminant.getType() == ContaminantType.REPLICANT)
            redYellowPool.add(contaminant);
        else
            installations.add(contaminant);
    }

    public void returnToPool(Countermeasure countermeasure){
        if (countermeasure.getType().isInstallation())
            guardiansFirewalls.add(countermeasure);
        else
            sparkFlarePool.add(countermeasure);
    }
}
