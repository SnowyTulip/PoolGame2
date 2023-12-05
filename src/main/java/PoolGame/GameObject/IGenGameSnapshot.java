package PoolGame.GameObject;

import PoolGame.GameObjectSnapshot.GameObjectSnapshot;
import PoolGame.GameObjectSnapshot.GameSnapshot;
/**
 * @projectName: Design_Pattern_Assignment_2
 * @package: PoolGame.GameObject
 * @className: IGenGameSnapshot
 * @author: pi
 * @description: 产生快照的接口,凡是需要在回合之间保存的Game对象都需要实现此接口
 * 以实现undo() redo()的功能
 * @version: 1.0
 */
public interface IGenGameSnapshot {
    public GameObjectSnapshot genSnapshot() ;
    public void setSnapshot(GameObjectSnapshot snapshot);
}
