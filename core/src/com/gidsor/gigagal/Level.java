package com.gidsor.gigagal;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gidsor.gigagal.entities.Bullet;
import com.gidsor.gigagal.entities.Enemy;
import com.gidsor.gigagal.entities.Explosion;
import com.gidsor.gigagal.entities.GigaGal;
import com.gidsor.gigagal.entities.Platform;
import com.gidsor.gigagal.entities.Powerup;
import com.gidsor.gigagal.util.Assets;
import com.gidsor.gigagal.util.Constants;
import com.gidsor.gigagal.util.Enums.*;

public class Level {
    public static final String TAG = Level.class.getName();

    private Viewport viewport;
    private GigaGal gigaGal;
    private Array<Platform> platforms;

    private DelayedRemovalArray<Enemy> enemies;
    private DelayedRemovalArray<Bullet> bullets;
    private DelayedRemovalArray<Explosion> explosions;
    private DelayedRemovalArray<Powerup> powerups;

    public Level(Viewport viewport) {
        this.viewport = viewport;
        initializeDebugLevel();
    }

    public void update(float delta) {
        gigaGal.update(delta, platforms);

        bullets.begin();
        for (Bullet bullet : bullets) {
            bullet.update(delta);

            if (!bullet.active) {
                bullets.removeValue(bullet, false);
            }
        }
        bullets.end();

        enemies.begin();
        for (Enemy enemy : enemies) {
            enemy.update(delta);

            if (enemy.health < 1) {
                spawnExplosion(enemy.position);
                enemies.removeValue(enemy, false);
            }
        }
        enemies.end();

        explosions.begin();
        for (Explosion explosion : explosions) {
            if (explosion.isFineshed()) {
                explosions.removeValue(explosion, false);
            }
        }
        explosions.end();
    }

    public void render(SpriteBatch batch) {
        for (Platform platform : platforms) {
            platform.render(batch);
        }

        for (Powerup powerup : powerups) {
            powerup.render(batch);
        }

        for (Enemy enemy : enemies) {
            enemy.render(batch);
        }

        gigaGal.render(batch);

        for (Bullet bullet : bullets) {
            bullet.render(batch);
        }

        for (Explosion explosion : explosions) {
            explosion.render(batch);
        }
    }

    private void initializeDebugLevel() {
        gigaGal = new GigaGal(new Vector2(14, 40), this);
        platforms = new Array<Platform>();
        enemies = new DelayedRemovalArray<Enemy>();
        bullets = new DelayedRemovalArray<Bullet>();
        explosions = new DelayedRemovalArray<Explosion>();
        powerups = new DelayedRemovalArray<Powerup>();

        platforms.add(new Platform(15, 100, 30, 20));

        Platform enemyPlatform = new Platform(75, 90, 100, 65);
        enemies.add(new Enemy(new Platform(35, 55, 50, 20)));
        platforms.add(enemyPlatform);

        platforms.add(new Platform(35, 55, 50, 20));
        platforms.add(new Platform(10, 20, 20, 9));

        powerups.add(new Powerup(new Vector2(20, 110)));
    }

    public Array<Platform> getPlatforms() {
        return platforms;
    }

    public Viewport getViewport() {
        return viewport;
    }

    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }

    public GigaGal getGigaGal() {
        return gigaGal;
    }

    public void setGigaGal(GigaGal gigaGal) {
        this.gigaGal = gigaGal;
    }

    public DelayedRemovalArray<Enemy> getEnemies() {
        return enemies;
    }

    public DelayedRemovalArray<Powerup> getPowerups() {
        return powerups;
    }

    public void spawnBullet(Vector2 position, Direction direction) {
        bullets.add(new Bullet(this, position, direction));
    }

    public void spawnExplosion(Vector2 position) {
        explosions.add(new Explosion(position));
    }
}
