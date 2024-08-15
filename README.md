# 桌球游戏

## 作者

[https://github.com/SnowyTulip/PoolGame2](https://github.com/SnowyTulip/PoolGame2)

![1701786633996](image/README/1701786633996.png)

## 使用声明

遵循开源协议 [WTFPL](https://zh.wikipedia.org/wiki/WTFPL)

## 命令说明

- 运行 `gradle run` 将使用 `resources` 文件夹中的默认配置文件。使用 `gradle run --args="${CONFIG_PATH}"` 来加载自定义配置文件。
- 运行 `gradle javadoc` 可以生成自动文档。

### Features

#### 1 修正物理引擎

使用真实物理摩擦力公式修正速度

![1701783582507](image/README/1701783582507.png)

#### 2 击球轨迹预测

使用公式计算轨迹落点

![1701785237112](image/README/1701785237112.png)

![1701783691412](image/README/1701783691412.png)

#### 3 击球动画优化

设置碰撞箱体，球杆拥有运动轨迹

![1701785268409](image/README/1701785268409.png)

![1701784584285](image/README/1701784584285.gif)

#### 4 图形优化，解决绿球显示的问题

![1701784678640](image/README/1701784678640.png)

#### 5 优化游戏胜利、游戏失败提示

![1701784941533](image/README/1701784941533.gif)

#### 6 难度控制选择

A 点击ESC键换出菜单，并暂停游戏

![1701785186871](image/README/1701785186871.gif)

#### 7 回合设置

回合策略：当一次击球开始时记为回合i开始

回合开始后所有球都停止运动记为回合结束

![1701785934920](image/README/1701785934920.gif)

#### 8 计时与得分

得分与计时均可回滚

![1701786210094](image/README/1701786210094.gif)
