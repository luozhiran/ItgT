##### ShadowLayer(控件阴影)

setShadowLayer构造函数
```markdown
public void setShadowLayer(float radius, float dx, float dy, int color);

```
radius :模糊半径,radius越大越模糊，越小越清晰，但是如果radius设置为0，则阴影消失不见

dx:阴影的横向偏移距离，正值向右偏移，负值向左偏移
dy:阴影的纵向偏移距离，正值向下偏移，负值向上偏移
color: 绘制阴影的画笔颜色，即阴影的颜色（对图片阴影无效）