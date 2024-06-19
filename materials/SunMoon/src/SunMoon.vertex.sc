$input a_position, a_texcoord0
$output v_texcoord0

#include <bgfx_shader.sh>

void main() {
    v_texcoord0 = a_texcoord0;
    
    vec3 pos = a_position;

  pos.xz *= 1.2;
    float angle = 15.0*0.0174533;
    float sinA = sin(angle);
    float cosA = cos(angle);
    pos.xz = vec2(pos.x*cosA - pos.z*sinA, pos.x*sinA + pos.z*cosA);
    
    gl_Position = mul(u_modelViewProj, vec4(pos, 1.0));
}