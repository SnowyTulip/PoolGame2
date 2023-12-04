package PoolGame.GameObject;

import PoolGame.GameObjectSnapshot.GameObjectSnapshot;
import PoolGame.GameObjectSnapshot.GameSnapshot;

public interface IGenGameSnapshot {
    public GameObjectSnapshot genSnapshot() ;
    public void setSnapshot(GameObjectSnapshot snapshot);
}
