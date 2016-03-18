/// <reference path="../../bower_components/polymer-ts/polymer-ts.d.ts" />
var __extends = (this && this.__extends) || function (d, b) {
    for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p];
    function __() { this.constructor = d; }
    d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
};
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var AvatarImage = (function (_super) {
    __extends(AvatarImage, _super);
    function AvatarImage() {
        console.log("constructor", this.pos());
        this._numTiles = 9;
        this._size = 40;
    }
    AvatarImage.prototype.attached = function () {
        console.log("attached", this.pos());
        this.updateAvatar();
    };
    AvatarImage.prototype._avatarChanged = function () {
        console.log("property changed", this.pos(), this.avatar);
        this.updateAvatar();
    };
    AvatarImage.prototype.updateAvatar = function () {
        console.log("udpating", this._size, this.avatar);
        if (this._size === undefined || this.avatar === undefined)
            return;
        var posX = -Math.round(this.avatar % this._numTiles * this._size);
        var poxY = -Math.round(Math.floor(this.avatar / this._numTiles) * this._size);
        var backgroundSize = Math.floor(this._numTiles * this._size);
        this.customStyle['--size'] = this._size + "px";
        this.customStyle['--background-position-x'] = posX + "px";
        this.customStyle['--background-position-y'] = poxY + "px";
        this.customStyle['--background-size'] = backgroundSize + "px";
        this.updateStyles();
    };
    AvatarImage.prototype.pos = function () {
        var list = this.closest('user-list');
        if (list) {
            return "(pos=" + ([].indexOf.call(list.querySelectorAll('avatar-image'), this) + 1) + ")";
        }
        else {
            return "(not attached)";
        }
    };
    __decorate([
        property({ name: "avatar", type: Number, observer: "_avatarChanged" })
    ], AvatarImage.prototype, "avatar", void 0);
    AvatarImage = __decorate([
        /// <reference path="../../bower_components/polymer-ts/polymer-ts.d.ts" />
        component("avatar-image")
    ], AvatarImage);
    return AvatarImage;
}(polymer.Base));
AvatarImage.register();
