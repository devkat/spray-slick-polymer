/// <reference path="../../bower_components/polymer-ts/polymer-ts.d.ts" />

@component("avatar-image")
class AvatarImage extends polymer.Base {

  private _numTiles: number;
  private _size: number;

  @property({ name: "avatar", type: Number, observer: "_avatarChanged" })
  avatar: number;

  constructor() {
    console.log("constructor", this.pos());
    this._numTiles = 9;
    this._size = 40;
  }

  attached(): void {
    console.log("attached", this.pos());
    this.updateAvatar();
  }

  _avatarChanged(): void {
    console.log("property changed", this.pos(), this.avatar);
    this.updateAvatar();
  }

  updateAvatar(): void {
    console.log("udpating", this._size, this.avatar);
    if (this._size === undefined || this.avatar === undefined) return;
    let posX = - Math.round(this.avatar % this._numTiles * this._size);
    let poxY = - Math.round(Math.floor(this.avatar / this._numTiles) * this._size);
    let backgroundSize = Math.floor(this._numTiles * this._size);
    this.customStyle['--size'] = `${this._size}px`;
    this.customStyle['--background-position-x'] = `${posX}px`;
    this.customStyle['--background-position-y'] = `${poxY}px`;
    this.customStyle['--background-size'] = `${backgroundSize}px`;
    this.updateStyles();
  }

  pos(): string {
    let list = this.closest('user-list');
    if (list) {
      return "(pos=" + ([].indexOf.call(list.querySelectorAll('avatar-image'), this) + 1) + ")";
    } else {
      return "(not attached)";
    }
  }

}

AvatarImage.register();