attribute vec4 a_position;
attribute vec4 a_color;
attribute vec2 a_texCoord0;
uniform mat4 u_projTrans;
uniform float angleWave;
uniform float amplitudeWave;
uniform float camPosX;
uniform float camPosY;
varying vec4 vColor;
varying vec2 vTexCoord;



void main() {
	vColor = a_color;
	vTexCoord = a_texCoord0;
	//on rescale a_position avec la position approximative de la camera sinon probleme avec les fonctions trigo
	//TODO: tester avec un framebuffer pour voir si on peut se passer de ça
	float sinWave = amplitudeWave * sin(angleWave + (a_position.x - camPosX) + (a_position.y - camPosY));
	float cosWave = amplitudeWave * cos(angleWave + (a_position.x - camPosX) + (a_position.y - camPosY));
	vec4 newPos = vec4(a_position.x + sinWave, a_position.y + cosWave, a_position.z, a_position.w);
	gl_Position = u_projTrans * newPos;
}
