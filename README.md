## TGRA Programming Assignment 2

### Spaceship vs. Asteroids - Survival of the Fittest

Okay, so. This is far from being finished, but works so far. Good for a prototype, I guess!

###### How to play

You move with W/S to active the forward and backward thrusters, use the arrow keys (←/→) to rotate the ship. Spacebar shoots your laser beams, you can shoot in a burst of three (3) every 2 seconds. You can also hold the left shift button to speed up the forward thrusters.

The goal is to avoid asteroids as best you can, either by dodging them and let them break up on their own, or shoot them down, breaking them up. Be careful though, as the new, smaller asteroids, shoot out in all directions, so don't be too close to them when they blow up!

#### Cool things

As simple as it is, we're pretty proud of the collision handling and how we remove and add asteroids during collisions. Though it's far from optimal and pretty rudimentary, it works pretty well. I'm pretty happy with the faux parallax and how you appear on the other side of the screen if you cross an edge.

##### TODO List
- [ ] Spaceship
  - [x] Add manual motion and rotation
    - [x] Rotation with arrow (←/→) keys, movement with W/S
  - [x] Fix edge collision of the window
    - [x] If hitting the edge, should just stop/loop around to the other side
  - [ ] Collision
    - [x] Simple sphere collision
    - [ ] More advanced, proper, collision detection
    - [ ] Rotation shouldn't matter here, any part (corners too) of the ship should ideally collide.
- [ ] Asteroids
  - [ ] Get random shapes
    - [ ] Can be pseudorandom selection of a set list of shapes
  - [x] Add motion
    - [x] Random motion of each asteroid, they should not move in a uniform fashion
    - [x] Asteroids should rotate slowly (doesn't show with current shapes)
  - [ ] Collision 
    - [x] Simple sphere collision
    - [ ] More advanced, proper, collision detection
    - [x] When two asteroids collide they should break into smaller (new) asteroids and shoot out into different directions
      - [x] Ideally these can then collide with each other, during the original collision, and further break up
- [ ] Background
  - [x] Black background with stars in the background
    - [x] These stars should be white and in different sizes
    - [x] These stars should be in _every_ quarter, not just x > 0 and y > 0
  - [ ] With enough time, add parallax scrolling when moving the ship
    - [x] Make the stars seem to move
    - [ ] Actual parallax scrolling with the ship movements

As can be seen on the above list, there's a lot we were not able to finish with this. Silly life and other responsibilities getting in the way!
