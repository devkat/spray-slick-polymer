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
var Avatar = (function (_super) {
    __extends(Avatar, _super);
    function Avatar() {
        _super.apply(this, arguments);
        this._tileSize = 185;
        this._numTiles = 9;
    }
    Avatar.prototype.idChanged = function () {
        this.customStyle['--position-x'] = (this.id % this._numTiles) * this._tileSize;
        this.customStyle['--position-y'] = Math.floor(this.id / this._numTiles) * this._tileSize;
    };
    __decorate([
        property({ name: "id", type: Number, observer: "idChanged" })
    ], Avatar.prototype, "id", void 0);
    Avatar = __decorate([
        /// <reference path="../../bower_components/polymer-ts/polymer-ts.d.ts" />
        component("avatar-image")
    ], Avatar);
    return Avatar;
}(polymer.Base));
Avatar.register();
