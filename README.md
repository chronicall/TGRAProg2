## TGRA Programming Assignment 2

### Spaceship vs. Asteroids - Survival

##### TODO List
- [ ] Spaceship
  - [ ] Add manual motion and rotation
    - [ ] Rotation with arrow (←/→) keys, movement with WASD
  - [ ] Fix edge collision of the window
    - [ ] If hitting the edge, should just stop
    - [ ] Rotation shouldn't matter here, any part (corners too) of the ship should ideally collide.
- [ ] Asteroids
  - [ ] Get random shapes
    - [ ] Can be pseudorandom selection of a set list of shapes
  - [ ] Add motion
    - [ ] Random motion of each asteroid, they should not move in a uniform fashion
    - [ ] Asteroids should rotate slowly
  - [ ] Collision 
    - [ ] Calculate collisions properly with vector math
    - [ ] When two asteroids collide they should break into smaller (new) asteroids and shoot out into different directions
      - [ ] Ideally these can then collide with each other, during the original collision, and further break up
- [ ] Background
  - [x] Black background with stars in the background
    - [x] These stars should be white and in different sizes
    - [x] These stars should be in _every_ quarter, not just x > 0 and y > 0
  - [ ] With enough time, add parallax scrolling when moving the ship
    - [ ] Make the stars seem to move
