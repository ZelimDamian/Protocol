directory.ProductListView = Backbone.View.extend({

    tagName:'table',
    
    className:'table table-striped',
    
    initialize:function () {
    	_.bindAll(this);
    	this.model.on("reset", this.render, this);
        this.model.on("add", function (product) {
            this.$el.append(new directory.ProductListItemView({model:product}).render().el);
        }, this);
    },

    render:function () {
        this.$el.empty();
        _.each(this.model.models, function (product) {
            this.$el.append(new directory.ProductListItemView({model:product}).render().el);
        }, this);
        return this;
    }
});

directory.ProductListItemView = Backbone.View.extend({

    tagName:"tr",

    events:{
    	'click' : function(element) {
    			Backbone.history.navigate('#products/' + this.model.id, {trigger : true});
    		},
    	'mouseover' : function(element) {
    			this.$el.addClass('success');
    		},
    	'mouseleave' : function()
    	{
    		this.$el.removeClass('success');
    	}
    },
    
    initialize:function () {
    	_.bindAll(this);
        this.model.on("destroy", this.close, this);
        this.model.on("sync", this.sync, this);
        
        this.$el.css( "cursor", "pointer" );
    },
    
    sync:function()
    {
    	this.render();
    	Backbone.history.navigate('#products/' + this.model.id, {trigger : true});
    },
    
    render:function () {
        this.$el.html(this.template(this.model.toJSON()));
        return this;
    }

});