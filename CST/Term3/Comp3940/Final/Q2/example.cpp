// （桥接）实现接口
interface Renderer {render();}
// 具体实现
class VectorRenderer implements Renderer {render() {/*vector render*/}}
class RasterRenderer implements Renderer {render() {/*raster render*/}}
// 抽象
abstract class Shape {
    Renderer renderer;
    Shape(renderer) {this.renderer = renderer;}
    draw();
}
// 细化抽象
class Circle extends Shape {draw() {renderer.render();}}
